package com.fadali.flod_restaurant.feature.auth

import com.fadali.flod_restaurant.feature.auth.data.AuthRepoImpl
import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthRemoteDataSource
import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthRemoteDataSourceImpl
import com.fadali.flod_restaurant.feature.auth.domain.AuthRepo
import com.fadali.flod_restaurant.feature.auth.domain.usecase.GetAuthTokensUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.GetUserIdUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.LoginUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.RefreshTokenUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.SaveAuthTokensUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.SaveUserIdUseCase
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

    factory<LoginUseCase> {
        LoginUseCase(get())
    }
    factory<ValidatePhoneNumberUseCase> {
        ValidatePhoneNumberUseCase()
    }
    factory<SignUpUseCase> {
        SignUpUseCase(get())
    }
    factory<ValidateOtpUseCase> {
        ValidateOtpUseCase(get())
    }
    single<GetAuthTokensUseCase> {
        GetAuthTokensUseCase(
            tokenStorage = get(),
            context = get()
        )
    }
    single<SaveAuthTokensUseCase> {
        SaveAuthTokensUseCase(
            tokenStorage = get(),
            context = get()
        )
    }
    single<SaveUserIdUseCase> {
        SaveUserIdUseCase(
            context = get(),
            dispatchers = get(),
        )
    }
    single<GetUserIdUseCase> {
        GetUserIdUseCase(
            context = get(),
            dispatchers = get(),
        )
    }
    single<RefreshTokenUseCase> {
        RefreshTokenUseCase(get())
    }

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
            userPrefsUseCase = get(),
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
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
}
