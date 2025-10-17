package com.fadali.flod_restaurant.feature.auth

import com.fadali.flod_restaurant.feature.auth.data.AuthRepoImpl
import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthRemoteDataSource
import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthRemoteDataSourceImpl
import com.fadali.flod_restaurant.feature.auth.domain.AuthRepo
import com.fadali.flod_restaurant.feature.auth.domain.usecase.LoginUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.LogoutUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.RefreshTokenUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.SignUpUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.ValidateOtpUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.ValidatePhoneNumberUseCase
import com.fadali.flod_restaurant.feature.auth.ui.login.LoginViewModel
import com.fadali.flod_restaurant.feature.auth.ui.otp.OTPVerificationViewModel
import com.fadali.flod_restaurant.feature.auth.ui.signup.SignUpViewModel
import com.fadali.flod_restaurant.feature.auth.ui.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    factory<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(get())
    }
    factory<AuthRepo> {
        AuthRepoImpl(get())
    }

    factory { LoginUseCase(get()) }
    factory { ValidatePhoneNumberUseCase() }
    factory { SignUpUseCase(get()) }
    factory { ValidateOtpUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { RefreshTokenUseCase(get()) }

    viewModel<SplashViewModel> {
        SplashViewModel(
            userPrefsUseCase = get(),
            onboardingUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get(),
        )
    }
    viewModel<LoginViewModel> {
        LoginViewModel(
            validatePhoneNumberUseCase = get(),
            loginUseCase = get(),
            saveUserIdUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get(),
        )
    }
    viewModel<SignUpViewModel> {
        SignUpViewModel(
            signUpUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
    viewModel<OTPVerificationViewModel> {
        OTPVerificationViewModel(
            validateOtpUseCase = get(),
            saveAuthTokensUseCase = get(),
            userPrefsUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
}
