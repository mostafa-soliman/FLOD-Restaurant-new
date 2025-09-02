package com.fadali.flod_restaurant.app.di

import org.koin.dsl.module

/**
 * A centralized Koin module for all the app DI modules.
 * Add your feature module inside the "includes" block.
 */
val appModule = module {
    includes(
        appNetworkModule,

    )
}
