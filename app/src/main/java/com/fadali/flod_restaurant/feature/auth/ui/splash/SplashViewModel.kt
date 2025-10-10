package com.fadali.flod_restaurant.feature.auth.ui.splash

import com.fadali.flod_restaurant.core.datastore.preferences.usecase.OnboardingUseCase
import com.fadali.flod_restaurant.core.datastore.preferences.usecase.UserPrefsUseCase
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class SplashViewModel(
    private val userPrefsUseCase: UserPrefsUseCase,
    private val onboardingUseCase: OnboardingUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<SplashState, SplashEvent, SplashEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {
    override fun initialState(): SplashState = SplashState()

    override fun handleEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.ShouldShowVideo -> handleVideoState()
            SplashEvent.OnSplashFinished -> onSplashVideoFinished()
        }
    }

    private fun handleVideoState() {
        launchSafe {
            val isOnboardingCompleted = onboardingUseCase.isOnboardingCompleted()
            if (isOnboardingCompleted) {
                navigateToNextScreen()
            } else {
                setState { copy(showVideo = true) }
            }
        }
    }

    private fun onSplashVideoFinished() {
        launchSafe {
            onboardingUseCase.markOnboardingCompleted()
            navigateToNextScreen()
        }
    }

    private fun navigateToNextScreen() {
        launchSafe {
            val isLoggedIn = userPrefsUseCase.isLoggedIn()
            if (isLoggedIn) {
                sendEffect(SplashEffect.NavigateToHome)
            } else {
                sendEffect(SplashEffect.NavigateToLogin)
            }
        }
    }
}
