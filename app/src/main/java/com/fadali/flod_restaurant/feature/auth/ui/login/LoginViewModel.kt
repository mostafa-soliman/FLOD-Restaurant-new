package com.fadali.flod_restaurant.feature.auth.ui.login

import com.fadali.flod_restaurant.feature.auth.domain.model.LoginRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.usecase.LoginUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.ValidatePhoneNumberUseCase
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class LoginViewModel(
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val loginUseCase: LoginUseCase,
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
            LoginEvent.OnSignUpClicked -> sendEffect(LoginEffect.NavigateToSignUp)
            LoginEvent.OnLanguageSelectorClicked -> {}
            LoginEvent.ValidatePhoneNumber -> validatePhoneNumber()
            LoginEvent.RemovePhoneNumberValidationError -> setState { copy(isValidPhoneNumber = true) }
        }
    }

    private fun onPhoneNumberChanged(event: LoginEvent.OnPhoneNumberChanged) {
        setState { copy(phoneNumber = event.phoneNumber) }
    }

    private fun onLoginClicked() {
        validatePhoneNumber()
        if (currentState.isValidPhoneNumber) {
            login()
        }
    }

    private fun validatePhoneNumber() {
        val finalNumber = validatePhoneNumberUseCase(currentState.phoneNumber)
        setState {
            copy(
                isValidPhoneNumber = finalNumber != null,
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
            setState { copy(isLoading = false) }
            if (response.notRegistered) {
                sendEffect(LoginEffect.NavigateToSignUp)
            } else {
                sendEffect(LoginEffect.NavigateToOtp(phoneNumber = currentState.finalPhoneNumber))
            }
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }
        sendEffect(LoginEffect.ShowError(message = throwable.message))
    }
}
