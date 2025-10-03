package com.fadali.flod_restaurant.feature.auth.domain.model.otp

sealed interface ValidateOtpRequest {
    data class Login(
        val otp: String,
        val userId: String,
    ) : ValidateOtpRequest

    data class SignUp(
        val phoneNumber: String,
        val otpCode: String,
    ) : ValidateOtpRequest
}
