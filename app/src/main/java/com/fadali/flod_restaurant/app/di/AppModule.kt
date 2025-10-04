package com.fadali.flod_restaurant.app.di

import com.fadali.flod_restaurant.core.authtokens.authTokenModule
import com.fadali.flod_restaurant.feature.auth.authModule
import com.smyvo.core.ui.uiModule
import org.koin.dsl.module

/**
 * A centralized Koin module for all the app DI modules.
 * Add your feature module inside the "includes" block.
 */
val appModule = module {
    includes(
        appNetworkModule,
        uiModule,
        mainModule,
        authModule,
        authTokenModule,
    )
}
