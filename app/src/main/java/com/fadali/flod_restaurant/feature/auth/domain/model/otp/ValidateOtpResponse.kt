package com.fadali.flod_restaurant.feature.auth.domain.model.otp

data class ValidateOtpResponse(
    val accessToken: String,
    val refreshToken: String,
    val needPasswordChange: Boolean,
)
