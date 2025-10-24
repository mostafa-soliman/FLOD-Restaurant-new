package com.fadali.flod_restaurant.app.di

import com.fadali.flod_restaurant.core.network.AppDefaultExceptionHandler
import com.fadali.flod_restaurant.core.network.FLODNetworkConfig
import com.fadali.flod_restaurant.core.network.appconfig.AppConfig
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
    single<FLODNetworkConfig> {
        FLODNetworkConfig(
            context = get(),
            getAuthTokensUseCase = get(),
            languageManager = get(),
        )
    }
    single<AppNetworkConfig> {
        get<FLODNetworkConfig>().networkConfig
    }
    single<AppConfig> {
        AppConfig.default
    }
}
