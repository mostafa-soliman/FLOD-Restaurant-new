package com.fadali.flod_restaurant.feature.auth

import com.fadali.flod_restaurant.feature.auth.data.AuthRepoImpl
import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthRemoteDataSource
import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthRemoteDataSourceImpl
import com.fadali.flod_restaurant.feature.auth.domain.AuthRepo
import com.fadali.flod_restaurant.feature.auth.domain.usecase.LoginUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.SignUpUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.ValidatePhoneNumberUseCase
import com.fadali.flod_restaurant.feature.auth.ui.login.LoginViewModel
import com.fadali.flod_restaurant.feature.auth.ui.signup.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    factory<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(get())
    }
    factory<AuthRepo> {
        AuthRepoImpl(get())
    }

    factory<LoginUseCase> {
        LoginUseCase(get())
    }
    factory<ValidatePhoneNumberUseCase> {
        ValidatePhoneNumberUseCase()
    }
    factory<SignUpUseCase> {
        SignUpUseCase(get())
    }

    viewModel<LoginViewModel> {
        LoginViewModel(
            validatePhoneNumberUseCase = get(),
            loginUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
    viewModel<SignUpViewModel> {
        SignUpViewModel(
            validatePhoneNumberUseCase = get(),
            signUpUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
}
