package com.fadali.flod_restaurant.core.network

import androidx.annotation.Keep
import com.smyvo.core.network.model.ErrorResponseModel
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AppResponseErrorModel(
    val statusCode: String?,
    val errors: String?,
    override val resultCode: String? = statusCode,
    override val errorMessage: String? = errors,
) : ErrorResponseModel
