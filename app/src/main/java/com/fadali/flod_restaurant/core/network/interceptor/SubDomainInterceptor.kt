package com.fadali.flod_restaurant.core.network.interceptor

import com.fadali.flod_restaurant.BuildConfig
import okhttp3.Interceptor

object SubDomainInterceptorFactory {
    const val ORIGIN_HEADER_KEY = "origin"

    fun create(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .addHeader(ORIGIN_HEADER_KEY, BuildConfig.SUB_DOMAIN)
                .build()

            chain.proceed(newRequest)
        }
    }
}
