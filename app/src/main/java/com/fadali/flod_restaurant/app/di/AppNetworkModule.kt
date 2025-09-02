package com.fadali.flod_restaurant.app.di

import com.example.core.network.AppNetworkConfig
import com.example.core.network.networkModule
import com.fadali.flod_restaurant.BuildConfig
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
