package com.fadali.flod_restaurant.feature.auth.data.model

import androidx.annotation.Keep
import com.fadali.flod_restaurant.BuildConfig
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class LoginRequestDataModel(
    val phoneNumber: String,
    val tenantId: String = BuildConfig.TENANT_ID
)
