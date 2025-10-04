package com.fadali.flod_restaurant.feature.auth.domain.usecase

import android.content.Context
import com.fadali.flod_restaurant.core.authtokens.AuthTokenStorage

class SaveAuthTokensUseCase(
    private val tokenStorage: AuthTokenStorage,
    private val context: Context
) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String
    ) {
        tokenStorage.saveTokens(
            context = context,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
