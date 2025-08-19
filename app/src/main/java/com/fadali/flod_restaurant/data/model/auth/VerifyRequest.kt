package com.fadali.flod_restaurant.data.model.auth

data class VerifyRequest(
    val mobileNumber: String,
    val code: String
)

