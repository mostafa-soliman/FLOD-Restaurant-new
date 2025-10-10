package com.fadali.flod_restaurant.core.network

import com.fadali.flod_restaurant.feature.auth.domain.model.refreshtoken.RefreshTokenRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.usecase.GetAuthTokensUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.GetUserIdUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.RefreshTokenUseCase
import com.fadali.flod_restaurant.feature.auth.domain.usecase.SaveAuthTokensUseCase
import com.smyvo.core.network.NetworkException
import com.smyvo.core.network.model.NetworkAuthTokens
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

/**
 * Wrapper class that handles lazy injection of RefreshTokenUseCase to avoid circular dependency
 */
class TokenRefreshHandler : KoinComponent {
    // Lazy injection - these will only be resolved when actually called
    private val refreshTokenUseCase: RefreshTokenUseCase by inject()
    private val getUserIdUseCase: GetUserIdUseCase by inject()
    private val saveAuthTokensUseCase: SaveAuthTokensUseCase by inject()
    private val getAuthTokensUseCase: GetAuthTokensUseCase by inject()

    suspend fun refreshTokens(): NetworkAuthTokens? {
        return try {
            val currentTokens = getAuthTokensUseCase()?.toNetworkAuthTokens() ?: return null
            val userId = getUserIdUseCase() ?: return null

            val refreshRequest = RefreshTokenRequestDomainModel(
                userId = userId,
                refreshToken = currentTokens.refreshToken
            )

            val newTokens = refreshTokenUseCase(refreshRequest)
            saveAuthTokensUseCase(
                accessToken = newTokens.accessToken,
                refreshToken = newTokens.refreshToken
            )

            NetworkAuthTokens(
                accessToken = newTokens.accessToken,
                refreshToken = newTokens.refreshToken
            )
        } catch (e: Exception) {
            when (e) {
                is NetworkException -> throw e
                else -> e.printStackTrace()
            }
            null
        }
    }
}
