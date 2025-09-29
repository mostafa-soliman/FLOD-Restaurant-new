package com.fadali.flod_restaurant.feature.auth.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SignUpResponseDataModel(
    val phoneNumber: String,
)
