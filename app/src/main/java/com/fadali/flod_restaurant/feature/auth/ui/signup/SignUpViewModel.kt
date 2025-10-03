package com.fadali.flod_restaurant.feature.auth.ui.signup

import android.util.Patterns
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.util.isOnlyLettersAndSpaces
import com.fadali.flod_restaurant.feature.auth.domain.model.Gender
import com.fadali.flod_restaurant.feature.auth.domain.model.SignUpRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.usecase.SignUpUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.ValidatePhoneNumberUseCase
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler
import java.time.LocalDate

class SignUpViewModel(
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val signUpUseCase: SignUpUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<SignUpState, SignUpEvent, SignUpEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {
    override fun initialState(): SignUpState = SignUpState()

    override fun handleEvent(event: SignUpEvent) {
        when (event) {
            SignUpEvent.LoadBirthDateRange -> loadBirthDateRange()
            is SignUpEvent.OnNameChanged -> onNameChanged(event)
            SignUpEvent.OnValidateName -> validateName()
            SignUpEvent.OnRemoveNameValidationError -> setState { copy(nameError = null) }
            is SignUpEvent.OnEmailChanged -> onEmailChanged(event)
            SignUpEvent.OnValidateEmail -> validateEmail()
            SignUpEvent.OnRemoveEmailValidationError -> setState { copy(emailError = null) }
            is SignUpEvent.OnPhoneNumberChanged -> onPhoneNumberChanged(event)
            SignUpEvent.OnValidatePhoneNumber -> validatePhoneNumber()
            SignUpEvent.OnRemovePhoneNumberValidationError -> setState { copy(phoneNumberError = null) }
            SignUpEvent.OnBirthdatePickerClicked -> setState { copy(isBirthdatePickerVisible = true) }
            is SignUpEvent.OnBirthdateSelected -> onBirthdateSelected(event)
            SignUpEvent.OnBirthdatePickerDismissed -> setState { copy(isBirthdatePickerVisible = false) }
            SignUpEvent.OnValidateBirthdate -> validateBirthdate()
            SignUpEvent.OnRemoveBirthdateValidationError -> setState { copy(birthdateError = null) }
            is SignUpEvent.OnGenderSelected -> onGenderSelected(event)
            SignUpEvent.OnNextBtnClicked -> onNextBtnClicked()
        }
    }

    private fun loadBirthDateRange() {
        val today = LocalDate.now()
        val minAge = 13L
        val maxAge = 100L
        val maxAllowed = today.minusYears(minAge)
        val minAllowed = today.minusYears(maxAge)
        val dateRange = minAllowed..maxAllowed
        setState { copy(birthDateRange = dateRange) }
    }

    private fun onNameChanged(event: SignUpEvent.OnNameChanged) {
        setState { copy(name = event.name) }
    }

    private fun onEmailChanged(event: SignUpEvent.OnEmailChanged) {
        setState { copy(email = event.email) }
    }

    private fun onPhoneNumberChanged(event: SignUpEvent.OnPhoneNumberChanged) {
        setState { copy(phoneNumber = event.phoneNumber) }
    }

    private fun onBirthdateSelected(event: SignUpEvent.OnBirthdateSelected) {
        setState {
            copy(
                birthdate = event.birthdate.toString(),
                isBirthdatePickerVisible = false
            )
        }
    }

    private fun onGenderSelected(event: SignUpEvent.OnGenderSelected) {
        setState { copy(gender = event.gender) }
    }

    private fun validateName() {
        val minNameLength = 3
        val name = currentState.name.trim()
        val nameError = when {
            name.isEmpty() || name.isBlank() -> R.string.name_is_required
            name.length < minNameLength -> R.string.name_min_characters_allowed_error
            name.isOnlyLettersAndSpaces().not() -> R.string.name_format_error
            else -> null
        }
        setState { copy(nameError = nameError) }
    }

    private fun validateEmail() {
        val email = currentState.email.trim()
        val isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val emailError = when {
            email.isEmpty() || email.isBlank() -> R.string.email_is_required
            !isValidEmail -> R.string.invalid_email_format
            else -> null
        }
        setState { copy(emailError = emailError) }
    }

    private fun validatePhoneNumber() {
        val phoneNumber = currentState.phoneNumber.trim()
        val finalNumber = validatePhoneNumberUseCase(phoneNumber)
        val isValidNumber = finalNumber != null
        val phoneNumberError = when {
            phoneNumber.isEmpty() || phoneNumber.isBlank() -> R.string.phone_number_is_required
            !isValidNumber -> R.string.invalid_phone_number
            else -> null
        }
        setState {
            copy(
                phoneNumberError = phoneNumberError,
                finalPhoneNumber = finalNumber ?: ""
            )
        }
    }

    private fun validateBirthdate() {
        val isValidBirthdate = currentState.birthdate.isNotEmpty()
        setState {
            copy(
                birthdateError = if (isValidBirthdate) null else R.string.birthday_is_required
            )
        }
    }

    private fun validateGender() {
        val isValidGender = currentState.gender != Gender.NONE
        setState {
            copy(
                genderError = if (isValidGender) null else R.string.gender_required_error
            )
        }
    }

    private fun validateAllFields(): Boolean {
        validateName()
        validateEmail()
        validatePhoneNumber()
        validateGender()
        validateBirthdate()
        val areValidFields = currentState.nameError == null &&
                currentState.emailError == null &&
                currentState.phoneNumberError == null &&
                currentState.birthdateError == null &&
                currentState.genderError == null

        return areValidFields
    }

    private fun onNextBtnClicked() {
        val areValidFields = validateAllFields()
        if (areValidFields) {
            setState { copy(isLoading = true) }
            launchSafe {
                val request = SignUpRequestDomainModel(
                    name = currentState.name,
                    email = currentState.email.lowercase(),
                    phoneNumber = currentState.phoneNumber,
                    gender = currentState.gender,
                    dateOfBirth = currentState.birthdate
                )
                val response = signUpUseCase(request)
                setState { copy(isLoading = false) }
                sendEffect(SignUpEffect.NavigateToOtp(phoneNumber = response.phoneNumber))
            }
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }
        sendEffect(SignUpEffect.ShowError(message = throwable.message))
    }

}
