package com.fadali.flod_restaurant.core.network

import android.content.Context
import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthEndPoints
import com.smyvo.core.network.AppNetworkConfig
import com.smyvo.core.network.android.interceptors.ChuckerInterceptorFactory
import com.smyvo.core.network.model.NetworkAuthTokens
import okhttp3.Interceptor

fun getAppNetworkConfig(context: Context): AppNetworkConfig {
    return AppNetworkConfig(
        appVersionName = BuildConfig.VERSION_NAME,
        deviceId = "unique-device-id-12345", // TODO: Replace with actual device ID retrieval logic
        acceptLanguage = "en-US", // TODO: Replace with actual language preference retrieval logic
        clientExceptionStatusCode = 422,
        errorResponseModel = AppResponseErrorModel::class,
        getAccessToken = { "" }, // TODO: Replace with actual access token retrieval logic
        getRefreshToken = { "" }, // TODO: Replace with actual refresh token retrieval logic
        refreshTokens = { NetworkAuthTokens("", "") }, // TODO: Replace with actual token refresh logic
        onRefreshTokenExpired = {  }, // TODO: Replace with actual refresh token expiry handling logic
        unAuthorizedEndpoints = getUnAuthorizedEndpoints(),
        interceptors = getInterceptors(context = context)
    )
}

fun getInterceptors(context: Context): List<Interceptor> = listOfNotNull(
    if (BuildConfig.DEBUG || BuildConfig.BUILD_TYPE == "staging") ChuckerInterceptorFactory.createDefault(context = context) else null,
)

fun getUnAuthorizedEndpoints(): List<String> = listOf(
    AuthEndPoints.ENDPOINT_LOGIN,
    AuthEndPoints.ENDPOINT_SIGN_UP,
    AuthEndPoints.ENDPOINT_VALIDATE_OTP_LOGIN,
    AuthEndPoints.ENDPOINT_VALIDATE_OTP_SIGN_UP,
    AuthEndPoints.ENDPOINT_REFRESH_TOKEN,
)
