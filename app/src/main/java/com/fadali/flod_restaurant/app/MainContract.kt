package com.fadali.flod_restaurant.app

import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class MainState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false,
) : UiState

sealed interface MainEvent : UiEvent{
    data object CheckLoginStatus : MainEvent
    data object OnToggleLanguage : MainEvent
    data object OnLogout : MainEvent
}

sealed interface MainEffect : UiEffect {
    data object LanguageToggled : MainEffect
    data object NavigateToLogin : MainEffect
    data class ShowError(val dialogState: ErrorDialogState? = null) : MainEffect
}
