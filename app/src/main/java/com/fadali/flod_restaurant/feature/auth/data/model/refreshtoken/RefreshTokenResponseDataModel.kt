package com.fadali.flod_restaurant.feature.auth.data.model.refreshtoken

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RefreshTokenResponseDataModel(
    val accessToken: String,
    val refreshToken: String,
    val needPasswordChange: Boolean,
)
