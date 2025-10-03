package com.fadali.flod_restaurant.feature.auth.data.model.otp

import androidx.annotation.Keep
import com.fadali.flod_restaurant.BuildConfig
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ValidateOtpLoginRequestDataModel(
    val otp: String,
    val userId: String,
    val tenantId: String = BuildConfig.TENANT_ID,
)
