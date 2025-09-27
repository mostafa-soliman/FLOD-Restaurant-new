package com.fadali.flod_restaurant.core.network

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ResponseModel<T>(
    val success: Boolean,
    val data: T?,
    val errors: String?,
    val statusCode: String
)
