package com.fadali.flod_restaurant.app.di

import com.fadali.flod_restaurant.app.MainViewModel
import org.koin.dsl.module

val mainModule = module {
    single {
        MainViewModel(
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
}
