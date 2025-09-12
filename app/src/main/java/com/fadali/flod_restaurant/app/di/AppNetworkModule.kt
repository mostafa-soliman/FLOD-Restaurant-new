package com.fadali.flod_restaurant.app.di

import com.fadali.flod_restaurant.BuildConfig
import com.smyvo.core.network.AppNetworkConfig
import com.smyvo.core.network.networkModule
import org.koin.dsl.module

val appNetworkModule = module {
    includes(
        networkModule
    )

    single<AppNetworkConfig> {
        AppNetworkConfig(
            appVersionName = BuildConfig.VERSION_NAME,
            deviceId = "unique-device-id-12345" // TODO: Replace with actual device ID retrieval logic
        )
    }
}
