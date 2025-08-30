package com.fadali.flod_restaurant.data.model.auth

import androidx.annotation.Keep

@Keep
data class SignUpResponse(
    val statusCode: Int,
    val data: SignUpUserData?,
    val success: Boolean,
    val message: String
)

@Keep
data class SignUpUserData(
    val userName: String,
    val mobileNumber: String,
    val email: String
)

