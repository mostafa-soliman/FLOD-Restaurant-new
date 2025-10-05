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
    object CheckLoginStatus : MainEvent
    object OnToggleLanguage : MainEvent
}

sealed interface MainEffect : UiEffect {
    object LanguageToggled : MainEffect
    data class ShowError(val dialogState: ErrorDialogState? = null) : MainEffect
}
