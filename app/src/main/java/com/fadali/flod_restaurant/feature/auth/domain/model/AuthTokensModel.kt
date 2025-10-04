package com.fadali.flod_restaurant.feature.auth.domain.model

import com.smyvo.core.network.model.NetworkAuthTokens

data class AuthTokensModel(
    val accessToken: String,
    val refreshToken: String
) {
    fun toNetworkAuthTokens() = NetworkAuthTokens(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}
