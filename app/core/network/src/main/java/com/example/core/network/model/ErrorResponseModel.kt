package com.example.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseModel(
    val resultCode: String? = null,
    val errorMessage: String? = null
)
