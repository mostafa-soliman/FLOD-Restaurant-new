package com.fadali.flod_restaurant.feature.auth.ui.otp.mapper

import com.fadali.flod_restaurant.feature.auth.domain.model.otp.ValidateOtpRequest
import com.fadali.flod_restaurant.feature.auth.ui.otp.OTPVerificationState
import com.fadali.flod_restaurant.feature.auth.ui.otp.OtpFlowType

fun OTPVerificationState.toValidateOtpRequest() = when (flowType) {
    OtpFlowType.LOGIN -> ValidateOtpRequest.Login(
        otp = otp,
        userId = userId
    )
    OtpFlowType.SIGN_UP -> ValidateOtpRequest.SignUp(
        phoneNumber = phoneNumber,
        otpCode = otp
    )
}
