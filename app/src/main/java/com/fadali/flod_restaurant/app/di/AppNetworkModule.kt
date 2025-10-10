package com.fadali.flod_restaurant.app.di

import com.fadali.flod_restaurant.core.network.AppDefaultExceptionHandler
import com.fadali.flod_restaurant.core.network.appconfig.AppConfig
import com.fadali.flod_restaurant.core.network.getAppNetworkConfig
import com.smyvo.core.network.AppNetworkConfig
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
        getAppNetworkConfig(
            context = get(),
            getAuthTokensUseCase = get(),
            languageManager = get(),
        )
    }
    single<AppConfig> {
        AppConfig.default
    }
}
