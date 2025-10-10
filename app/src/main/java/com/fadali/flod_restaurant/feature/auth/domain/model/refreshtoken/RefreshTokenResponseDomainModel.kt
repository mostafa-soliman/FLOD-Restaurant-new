package com.fadali.flod_restaurant.feature.auth.domain.model.refreshtoken

data class RefreshTokenResponseDomainModel(
    val accessToken: String,
    val refreshToken: String,
    val needPasswordChange: Boolean,
)
