package com.fadali.flod_restaurant.feature.auth.data.model.refreshtoken

import androidx.annotation.Keep
import com.fadali.flod_restaurant.BuildConfig
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RefreshTokenRequestDataModel(
    val userId: String,
    val tenantId: String = BuildConfig.TENANT_ID,
    val refreshToken: String,
)
