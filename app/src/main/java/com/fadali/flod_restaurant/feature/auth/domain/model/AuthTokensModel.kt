package com.fadali.flod_restaurant.feature.auth.domain.model

data class AuthTokensModel(
    val accessToken: String,
    val refreshToken: String
)
