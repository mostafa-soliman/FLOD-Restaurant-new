package com.fadali.flod_restaurant.data.model.auth

import androidx.annotation.Keep

@Keep
data class LoginResponse(
    val statusCode: Int,
    val data: UserData?,
    val success: Boolean,
    val message: String?
)

@Keep
data class UserData(
    val userId: String,
    val userName: String,
    val mobileNumber: String
)
