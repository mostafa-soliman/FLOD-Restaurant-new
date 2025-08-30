package com.fadali.flod_restaurant.data.model.auth

import androidx.annotation.Keep

@Keep
data class SignUpRequest(
    val name: String,
    val email: String,
    val mobileNumber: String,
    val birthDate: String,
    val gender: Int
)