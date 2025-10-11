package com.fadali.flod_restaurant.core.network

import android.content.Context
import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguageManager
import com.fadali.flod_restaurant.core.network.interceptor.SubDomainInterceptorFactory
import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthEndpoints
import com.fadali.flod_restaurant.feature.auth.domain.usecase.GetAuthTokensUseCase
import com.smyvo.core.network.AppNetworkConfig
import com.smyvo.core.network.android.interceptors.ChuckerInterceptorFactory
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor


fun getAppNetworkConfig(
    context: Context,
    getAuthTokensUseCase: GetAuthTokensUseCase,
    languageManager: LanguageManager,
): AppNetworkConfig {
    val tokenRefreshHandler = TokenRefreshHandler()
    
    return AppNetworkConfig(
        appVersionName = BuildConfig.VERSION_NAME,
        deviceId = "unique-device-id-12345", // TODO: Replace with actual device ID retrieval logic
        acceptLanguage = getAcceptLanguage(
            languageManager = languageManager,
            context = context
        ),
        clientExceptionStatusCode = 422,
        errorResponseModel = AppResponseErrorModel::class,
        getAuthTokens = { getAuthTokensUseCase()?.toNetworkAuthTokens() },
        refreshTokens = { tokenRefreshHandler.refreshTokens() },
        onRefreshTokenExpired = { }, // TODO: Replace with actual refresh token expiry handling logic
        unAuthorizedEndpoints = getUnAuthorizedEndpoints(),
        interceptors = getInterceptors(context = context)
    )
}

private fun getAcceptLanguage(languageManager: LanguageManager, context: Context): String {
    var currentLanguage = "en-US"
    runBlocking {
        currentLanguage = languageManager.getCurrentLanguage(context)
    }
    return when (currentLanguage) {
        LanguageManager.ARABIC -> "ar-SA"
        LanguageManager.ENGLISH -> "en-US"
        else -> "en-US"
    }
}

private fun getInterceptors(context: Context): List<Interceptor> = listOfNotNull(
    if (BuildConfig.DEBUG || BuildConfig.BUILD_TYPE == "staging") ChuckerInterceptorFactory.createDefault(context = context) else null,
    SubDomainInterceptorFactory.create(),
)

private fun getUnAuthorizedEndpoints(): List<String> = listOf(
    AuthEndpoints.ENDPOINT_LOGIN,
    AuthEndpoints.ENDPOINT_SIGN_UP,
    AuthEndpoints.ENDPOINT_VALIDATE_OTP_LOGIN,
    AuthEndpoints.ENDPOINT_VALIDATE_OTP_SIGN_UP,
    AuthEndpoints.ENDPOINT_REFRESH_TOKEN,
)
