package com.fadali.flod_restaurant.feature.auth.ui.splash

import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class SplashState(
    val isLoading: Boolean = true,
    val showVideo: Boolean = false,
) : UiState

sealed interface SplashEvent : UiEvent {
    data object OnSplashFinished : SplashEvent
    data object ShouldShowVideo : SplashEvent
}

sealed interface SplashEffect : UiEffect {
    data object NavigateToLogin : SplashEffect
    data object NavigateToHome : SplashEffect
}
