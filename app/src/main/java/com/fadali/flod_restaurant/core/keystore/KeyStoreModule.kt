package com.fadali.flod_restaurant.core.keystore

import com.fadali.flod_restaurant.core.keystore.usecase.authtoken.GetAuthTokensUseCase
import com.fadali.flod_restaurant.core.keystore.usecase.authtoken.SaveAuthTokensUseCase
import com.fadali.flod_restaurant.core.keystore.usecase.userid.DeleteUserIdUseCase
import com.fadali.flod_restaurant.core.keystore.usecase.userid.GetUserIdUseCase
import com.fadali.flod_restaurant.core.keystore.usecase.userid.SaveUserIdUseCase
import org.koin.dsl.module

val keyStoreModule = module {
    factory {
        SaveUserIdUseCase(
            context = get(),
            dispatchers = get(),
        )
    }
    factory {
        GetUserIdUseCase(
            context = get(),
            dispatchers = get(),
        )
    }
    factory {
        DeleteUserIdUseCase(
            context = get(),
            dispatchers = get(),
        )
    }
    factory {
        SaveAuthTokensUseCase(
            tokenStorage = get(),
            context = get()
        )
    }
    factory {
        GetAuthTokensUseCase(
            tokenStorage = get(),
            context = get()
        )
    }
}
