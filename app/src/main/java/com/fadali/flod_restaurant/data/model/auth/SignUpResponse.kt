package com.fadali.flod_restaurant.data.model.auth

data class SignUpResponse(
    val statusCode: Int,
    val data: SignUpUserData?,
    val success: Boolean,
    val message: String
)

data class SignUpUserData(
    val userName: String,
    val mobileNumber: String,
    val email: String
)

