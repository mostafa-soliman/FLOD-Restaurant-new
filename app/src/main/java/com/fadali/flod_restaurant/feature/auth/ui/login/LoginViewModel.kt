package com.fadali.flod_restaurant.feature.auth.ui.login

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.usecase.LoginUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.SaveUserIdUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.ValidatePhoneNumberUseCase
import com.fadali.flod_restaurant.feature.auth.ui.otp.OtpFlowType
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class LoginViewModel(
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val loginUseCase: LoginUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {
    override fun initialState(): LoginState = LoginState()

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnPhoneNumberChanged -> onPhoneNumberChanged(event)
            LoginEvent.OnLoginClicked -> onLoginClicked()
            LoginEvent.OnLanguageSelectorClicked -> onLanguageSelectorClicked()
            LoginEvent.ValidatePhoneNumber -> validatePhoneNumber()
            LoginEvent.RemovePhoneNumberValidationError -> setState { copy(phoneNumberError = null) }
        }
    }

    private fun onPhoneNumberChanged(event: LoginEvent.OnPhoneNumberChanged) {
        setState { copy(phoneNumber = event.phoneNumber) }
    }

    private fun onLanguageSelectorClicked() {
        sendEffect(LoginEffect.LanguageToggled)
    }

    private fun onLoginClicked() {
        validatePhoneNumber()
        if (currentState.phoneNumberError == null) {
            login()
        }
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

    private fun login() {
        setState { copy(isLoading = true) }
        launchSafe {
            val request = LoginRequestDomainModel(
                phoneNumber = currentState.finalPhoneNumber
            )
            val response = loginUseCase(request)
            saveUserIdUseCase(response.customerId)
            setState { copy(isLoading = false) }
            if (response.notRegistered) {
                sendEffect(
                    LoginEffect.NavigateToNextScreen(
                        route = AuthRoutes.SignUp(phone = currentState.finalPhoneNumber)
                    )
                )
            } else {
                sendEffect(
                    LoginEffect.NavigateToNextScreen(
                        route = AuthRoutes.OtpVerification(
                            userId = response.customerId,
                            phone = currentState.finalPhoneNumber,
                            flowType = OtpFlowType.LOGIN
                        )
                    )
                )
            }
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }
        val errorMessage = throwable.message?.let {
            if (it.isEmpty()) return@let null
            it
        }
        sendEffect(LoginEffect.ShowError(message = errorMessage))
    }
}
