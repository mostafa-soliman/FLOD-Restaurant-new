package com.fadali.flod_restaurant.feature.auth.data.model.otp

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ValidateOtpLoginResponseDataModel(
    val accessToken: String,
    val refreshToken: String,
    val needPasswordChange: Boolean,
)
