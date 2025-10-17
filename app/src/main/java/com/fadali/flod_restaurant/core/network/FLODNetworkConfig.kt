package com.fadali.flod_restaurant.core.network

import android.content.Context
import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguageManager
import com.fadali.flod_restaurant.core.network.interceptor.SubDomainInterceptorFactory
import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthEndpoints
import com.fadali.flod_restaurant.core.keystore.usecase.authtoken.GetAuthTokensUseCase
import com.smyvo.core.network.AppNetworkConfig
import com.smyvo.core.network.NetworkConstants
import com.smyvo.core.network.android.interceptors.ChuckerInterceptorFactory
import com.smyvo.core.network.interceptors.AuthRefreshInterceptorFactory
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor

class FLODNetworkConfig(
    private val context: Context,
    private val getAuthTokensUseCase: GetAuthTokensUseCase,
    private val languageManager: LanguageManager,
) {
    private val tokenRefreshHandler = TokenRefreshHandler()

    var networkConfig: AppNetworkConfig
        private set

    init {
        networkConfig = AppNetworkConfig(
            appVersionName = BuildConfig.VERSION_NAME,
            platform = NetworkConstants.PLATFORM_ANDROID,
            deviceId = null,
            acceptLanguage = getAcceptLanguage(),
            clientExceptionStatusCode = 422,
            errorResponseModel = AppResponseErrorModel::class,
            interceptors = getInterceptors()
        )
    }

    private fun getAcceptLanguage(): String {
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

    private fun createAuthInterceptor(): Interceptor {
        return AuthRefreshInterceptorFactory.create(
            getAccessToken = { getAuthTokensUseCase()?.toNetworkAuthTokens()?.accessToken },
            refreshTokens = {
                tokenRefreshHandler.refreshTokens()
                true
            },
            onRefreshTokenExpired = { tokenRefreshHandler.onRefreshTokenExpired() },
            unAuthorizedEndpoints = getUnAuthorizedEndpoints()
        )
    }

    private fun getInterceptors(): List<Interceptor> = listOfNotNull(
        if (BuildConfig.DEBUG || BuildConfig.BUILD_TYPE == "staging") ChuckerInterceptorFactory.createDefault(context = context) else null,
        SubDomainInterceptorFactory.create(),
        createAuthInterceptor(),
    )

    private fun getUnAuthorizedEndpoints(): List<String> = listOf(
        AuthEndpoints.ENDPOINT_LOGIN,
        AuthEndpoints.ENDPOINT_SIGN_UP,
        AuthEndpoints.ENDPOINT_VALIDATE_OTP_LOGIN,
        AuthEndpoints.ENDPOINT_VALIDATE_OTP_SIGN_UP,
        AuthEndpoints.ENDPOINT_REFRESH_TOKEN,
    )
}

