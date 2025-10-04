package com.fadali.flod_restaurant.feature.auth.ui.signup

import androidx.annotation.StringRes
import com.fadali.flod_restaurant.feature.auth.domain.model.Gender
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState
import java.time.LocalDate

data class SignUpState(
    val isLoading: Boolean = false,
    val name: String = "",
    @StringRes val nameError: Int? = null,
    val email: String = "",
    @StringRes val emailError: Int? = null,
    val phoneNumber: String = "",
    @StringRes val phoneNumberError: Int? = null,
    val finalPhoneNumber: String = "",
    val birthdate: String = "",
    val displayedBirthdate: String = "",
    val isBirthdatePickerVisible: Boolean = false,
    @StringRes val birthdateError: Int? = null,
    val birthDateRange: ClosedRange<LocalDate> = LocalDate.of(1900, 1, 1)..LocalDate.now(),
    val gender: Gender = Gender.NONE,
    @StringRes val genderError: Int? = null,
) : UiState

sealed interface SignUpEvent : UiEvent {
    data object LoadBirthDateRange : SignUpEvent

    data class OnNameChanged(val name: String) : SignUpEvent
    data object OnValidateName : SignUpEvent
    data object OnRemoveNameValidationError : SignUpEvent

    data class OnEmailChanged(val email: String) : SignUpEvent
    data object OnValidateEmail : SignUpEvent
    data object OnRemoveEmailValidationError : SignUpEvent

    data class OnPhoneNumberChanged(val phoneNumber: String) : SignUpEvent
    data object OnValidatePhoneNumber : SignUpEvent
    data object OnRemovePhoneNumberValidationError : SignUpEvent

    data object OnBirthdatePickerClicked : SignUpEvent
    data class OnBirthdateSelected(val birthdate: LocalDate) : SignUpEvent
    data object OnBirthdatePickerDismissed : SignUpEvent
    data object OnValidateBirthdate : SignUpEvent
    data object OnRemoveBirthdateValidationError : SignUpEvent

    data class OnGenderSelected(val gender: Gender) : SignUpEvent
    data object OnNextBtnClicked : SignUpEvent
}

sealed interface SignUpEffect : UiEffect {
    data class NavigateToOtp(val phoneNumber: String) : SignUpEffect
    data class ShowError(val message: String? = null) : SignUpEffect
}
