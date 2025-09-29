package com.fadali.flod_restaurant.app.di

import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.network.AppDefaultExceptionHandler
import com.fadali.flod_restaurant.core.network.AppResponseErrorModel
import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthEndPoints
import com.smyvo.core.network.AppNetworkConfig
import com.smyvo.core.network.model.NetworkAuthTokens
import com.smyvo.core.network.networkModule
import com.smyvo.core.ui.network.DefaultExceptionHandler
import org.koin.dsl.module

val appNetworkModule = module {
    includes(
        networkModule
    )
    single<DefaultExceptionHandler> {
        AppDefaultExceptionHandler()
    }
    single<AppNetworkConfig> {
        AppNetworkConfig(
            appVersionName = BuildConfig.VERSION_NAME,
            deviceId = "unique-device-id-12345", // TODO: Replace with actual device ID retrieval logic
            acceptLanguage = "en-US", // TODO: Replace with actual language preference retrieval logic
            clientExceptionStatusCode = 422,
            errorResponseModel = AppResponseErrorModel::class,
            getAccessToken = { "" }, // TODO: Replace with actual access token retrieval logic
            getRefreshToken = { "" }, // TODO: Replace with actual refresh token retrieval logic
            refreshTokens = { NetworkAuthTokens("", "") }, // TODO: Replace with actual token refresh logic
            onRefreshTokenExpired = {  }, // TODO: Replace with actual refresh token expiry handling logic
            unAuthorizedEndpoints = listOf(
                AuthEndPoints.ENDPOINT_LOGIN,
                AuthEndPoints.ENDPOINT_SIGN_UP,
                AuthEndPoints.ENDPOINT_VALIDATE_OTP_LOGIN,
                AuthEndPoints.ENDPOINT_VALIDATE_OTP_SIGN_UP,
                AuthEndPoints.ENDPOINT_REFRESH_TOKEN,
            ),
        )
    }
}
