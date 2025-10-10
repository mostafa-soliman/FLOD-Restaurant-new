package com.fadali.flod_restaurant.feature.auth.domain.model.refreshtoken

data class RefreshTokenRequestDomainModel(
    val userId: String,
    val refreshToken: String,
)
