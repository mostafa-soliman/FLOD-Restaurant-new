package com.fadali.flod_restaurant.feature.auth.data.model.otp

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ValidateOtpLoginRequestDataModel(
    val otp: String,
    val userId: String,
)
