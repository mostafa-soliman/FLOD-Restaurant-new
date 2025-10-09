package com.fadali.flod_restaurant.feature.home

import com.fadali.flod_restaurant.feature.home.ui.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
}
