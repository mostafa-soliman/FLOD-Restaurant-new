package com.fadali.flod_restaurant.core.network.interceptor

import com.smyvo.core.network.NetworkConstants.AUTHORIZATION_HEADER
import com.smyvo.core.network.NetworkConstants.BEARER_TOKEN_PREFIX
import okhttp3.Interceptor

object AuthInterceptorFactory {

    fun create(
        accessToken: String?,
        unAuthorizedEndpoints: List<String>
    ): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestUrl = originalRequest.url.toString()

            val shouldSkipAuth = unAuthorizedEndpoints.any { endpoint ->
                requestUrl.contains(endpoint, ignoreCase = true)
            }
            if (shouldSkipAuth) {
                chain.proceed(originalRequest)
            }

            val requestWithAuth = if (accessToken != null) {
                originalRequest.newBuilder()
                    .header(AUTHORIZATION_HEADER, "$BEARER_TOKEN_PREFIX$accessToken")
                    .build()
            } else {
                originalRequest
            }

            chain.proceed(requestWithAuth)
        }
    }
}
