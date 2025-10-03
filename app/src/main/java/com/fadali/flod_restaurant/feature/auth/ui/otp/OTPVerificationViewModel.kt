package com.fadali.flod_restaurant.feature.auth.ui.otp

import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.auth.domain.usecase.ValidateOtpUseCase
import com.fadali.flod_restaurant.feature.auth.ui.otp.mapper.toNextScreenRoute
import com.fadali.flod_restaurant.feature.auth.ui.otp.mapper.toValidateOtpRequest
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class OTPVerificationViewModel(
    private val validateOtpUseCase: ValidateOtpUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<OTPVerificationState, OTPVerificationEvent, OTPVerificationEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler,
) {
    override fun initialState(): OTPVerificationState = OTPVerificationState()

    override fun handleEvent(event: OTPVerificationEvent) {
        when (event) {
            is OTPVerificationEvent.LoadArgs -> onLoadArgs(event)
            OTPVerificationEvent.OnCompleteOTP -> onCompleteOTP()
            is OTPVerificationEvent.OnOTPChanged -> setState { copy(otp = event.otp) }
            OTPVerificationEvent.OnTimerFinished -> onTimerFinished()
            OTPVerificationEvent.OnResendClicked -> onResendClicked()
        }
    }

    private fun onLoadArgs(event: OTPVerificationEvent.LoadArgs) {
        setState {
            copy(
                flowType = event.args.flowType,
                userId = event.args.userId,
                phoneNumber = event.args.phone,
            )
        }
    }

    private fun onCompleteOTP() {
        setState {
            copy(
                isLoading = true,
                otpInlineError = null
            )
        }
        launchSafe {
            val request = currentState.toValidateOtpRequest()
            val response = validateOtpUseCase(request)
            // TODO: store tokens in keystore
            setState { copy(isLoading = false) }
            sendEffect(
                OTPVerificationEffect.NavigateToNextScreen(
                    route = currentState.flowType.toNextScreenRoute()
                )
            )
        }
    }

    private fun onTimerFinished() {
        setState {
            copy(
                isResendEnabled = true,
                timeLeft = 0
            )
        }
    }

    private fun onResendClicked() {
        setState {
            // TODO: Call resend OTP API
            copy(
                isResendEnabled = false,
                timeLeft = currentState.otpSettings.expireByMinutes.times(60L)
            )
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }

        val errorMessage = throwable.message?.let {
            if (it.isEmpty()) return@let null
            else DeferredString.StringValue(it)
        } ?: ErrorDialogState.default.message

        sendEffect(
            OTPVerificationEffect.ShowError(
                dialogState = ErrorDialogState.default.copy(
                    message = errorMessage,
                    onPrimaryBtnClicked = {}
                )
            )
        )
    }
}
