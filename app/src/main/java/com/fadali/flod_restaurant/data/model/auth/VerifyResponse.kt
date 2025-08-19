package com.fadali.flod_restaurant.data.model.auth

data class VerifyResponse(
    val statusCode: Int,
    val data: VerifyData?,
    val success: Boolean,
    val message: String
)

data class VerifyData(
    val token: String,
    val userId: String,
    val userName: String,
    val mobileNumber: String
)

