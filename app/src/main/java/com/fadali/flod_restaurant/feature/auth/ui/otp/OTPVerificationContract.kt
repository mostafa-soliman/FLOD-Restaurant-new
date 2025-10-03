package com.fadali.flod_restaurant.feature.auth.ui.otp

import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.app.navigation.routes.Route
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.network.appconfig.AppConfig
import com.fadali.flod_restaurant.core.network.appconfig.OtpSettings
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class OTPVerificationState(
    val isLoading: Boolean = false,
    val flowType: OtpFlowType = OtpFlowType.LOGIN,
    val phoneNumber: String = "",
    val userId: String = "",
    val otp: String = "",
    val otpSettings: OtpSettings = AppConfig.default.otpSettings,
    val otpInlineError: String? = null,
    val isResendEnabled: Boolean = false,
    val timeLeft: Long = otpSettings.expireByMinutes.times(60L),
) : UiState

sealed interface OTPVerificationEvent : UiEvent {
    data class LoadArgs(val args: AuthRoutes.OtpVerification) : OTPVerificationEvent
    data class OnOTPChanged(val otp: String) : OTPVerificationEvent
    data object OnCompleteOTP : OTPVerificationEvent
    data object OnTimerFinished : OTPVerificationEvent
    data object OnResendClicked : OTPVerificationEvent
}

sealed interface OTPVerificationEffect : UiEffect {
    data class NavigateToNextScreen(val route: Route) : OTPVerificationEffect
    data class ShowError(val dialogState: ErrorDialogState? = null) : OTPVerificationEffect
}
