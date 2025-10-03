package com.fadali.flod_restaurant.feature.auth.ui.login

import androidx.annotation.StringRes
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class LoginState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    @StringRes val phoneNumberError: Int? = null,
    val finalPhoneNumber: String = "",
) : UiState

sealed interface LoginEvent : UiEvent {
    data class OnPhoneNumberChanged(val phoneNumber: String) : LoginEvent
    data object OnLoginClicked : LoginEvent
    data object OnSignUpClicked : LoginEvent
    data object OnLanguageSelectorClicked : LoginEvent
    data object ValidatePhoneNumber : LoginEvent
    data object RemovePhoneNumberValidationError : LoginEvent
}

sealed interface LoginEffect : UiEffect {
    data class NavigateToOtp(
        val userId: String,
        val phoneNumber: String
    ) : LoginEffect
    data object NavigateToSignUp : LoginEffect
    data class ShowError(val message: String? = null) : LoginEffect
}
