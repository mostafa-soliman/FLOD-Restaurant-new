package com.fadali.flod_restaurant.feature.auth.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SignUpRequestDataModel(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val gender: Int,
    val dateOfBirth: String,
)
