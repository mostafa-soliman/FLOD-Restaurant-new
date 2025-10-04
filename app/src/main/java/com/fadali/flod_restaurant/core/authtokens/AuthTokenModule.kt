package com.fadali.flod_restaurant.core.authtokens

import org.koin.dsl.module

val authTokenModule = module {
    single<AuthTokenStorage> {
        AuthTokenStorage(dispatchers = get())
    }
}
