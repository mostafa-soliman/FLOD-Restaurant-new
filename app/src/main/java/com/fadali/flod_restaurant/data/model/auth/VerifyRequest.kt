package com.fadali.flod_restaurant.data.model.auth

import androidx.annotation.Keep

@Keep
data class VerifyRequest(
    val mobileNumber: String,
    val code: String
)

