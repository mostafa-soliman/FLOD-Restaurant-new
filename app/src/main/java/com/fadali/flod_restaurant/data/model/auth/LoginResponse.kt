package com.fadali.flod_restaurant.data.model.auth

data class LoginResponse(
    val statusCode: Int,
    val data: UserData?,
    val success: Boolean,
    val message: String?
)

data class UserData(
    val userId: String,
    val userName: String,
    val mobileNumber: String
)
