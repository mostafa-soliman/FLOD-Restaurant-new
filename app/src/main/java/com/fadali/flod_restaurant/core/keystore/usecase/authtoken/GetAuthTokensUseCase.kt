package com.fadali.flod_restaurant.core.keystore.usecase.authtoken

import android.content.Context
import com.fadali.flod_restaurant.core.authtokens.AuthTokenStorage
import com.fadali.flod_restaurant.feature.auth.domain.model.AuthTokensModel

class GetAuthTokensUseCase(
    private val tokenStorage: AuthTokenStorage,
    private val context: Context
) {
    suspend operator fun invoke(): AuthTokensModel? {
        val tokens = tokenStorage.loadTokens(context = context)
        return AuthTokensModel(
            accessToken = tokens.first ?: return null,
            refreshToken = tokens.second ?: return null
        )
    }
}
