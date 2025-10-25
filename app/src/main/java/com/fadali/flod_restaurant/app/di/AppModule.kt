package com.fadali.flod_restaurant.app.di

import com.fadali.flod_restaurant.core.authtokens.authTokenModule
import com.fadali.flod_restaurant.core.datastore.coreDataStoreModule
import com.fadali.flod_restaurant.core.keystore.keyStoreModule
import com.fadali.flod_restaurant.feature.auth.authModule
import com.fadali.flod_restaurant.feature.home.homeModule
import com.fadali.flod_restaurant.feature.plan.planModule
import com.fadali.flod_restaurant.feature.profile.profileModule
import com.fadali.flod_restaurant.feature.subscription.subscriptionModule
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
        coreDataStoreModule,
        keyStoreModule,
        authModule,
        authTokenModule,
        profileModule,
        homeModule,
        subscriptionModule,
        planModule,
    )
}
