package com.fadali.flod_restaurant.data.model.auth

import androidx.annotation.Keep

@Keep
data class VerifyResponse(
    val statusCode: Int,
    val data: VerifyData?,
    val success: Boolean,
    val message: String
)

@Keep
data class VerifyData(
    val token: String,
    val userId: String,
    val userName: String,
    val mobileNumber: String
)

