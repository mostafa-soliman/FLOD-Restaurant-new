package com.fadali.flod_restaurant.core.network

import com.fadali.flod_restaurant.app.MainEvent
import com.fadali.flod_restaurant.app.MainViewModel
import com.fadali.flod_restaurant.core.datastore.preferences.usecase.UserPrefsUseCase
import com.fadali.flod_restaurant.core.keystore.usecase.authtoken.GetAuthTokensUseCase
import com.fadali.flod_restaurant.core.keystore.usecase.authtoken.SaveAuthTokensUseCase
import com.fadali.flod_restaurant.core.keystore.usecase.userid.DeleteUserIdUseCase
import com.fadali.flod_restaurant.core.keystore.usecase.userid.GetUserIdUseCase
import com.fadali.flod_restaurant.feature.auth.domain.model.refreshtoken.RefreshTokenRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.usecase.RefreshTokenUseCase
import com.smyvo.core.network.model.NetworkAuthTokens
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Wrapper class that handles refreshing tokens and related actions.
 */
class TokenRefreshHandler : KoinComponent {
    // Lazy injection - these will only be resolved when actually called
    private val refreshTokenUseCase: RefreshTokenUseCase by inject()
    private val getUserIdUseCase: GetUserIdUseCase by inject()
    private val saveAuthTokensUseCase: SaveAuthTokensUseCase by inject()
    private val getAuthTokensUseCase: GetAuthTokensUseCase by inject()
    private val userPrefsUseCase: UserPrefsUseCase by inject()
    private val deleteUserIdUseCase: DeleteUserIdUseCase by inject()
    private val mainViewModel: MainViewModel by inject()

    suspend fun refreshTokens(): NetworkAuthTokens? {
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

        return NetworkAuthTokens(
            accessToken = newTokens.accessToken,
            refreshToken = newTokens.refreshToken
        )
    }

    suspend fun onRefreshTokenExpired() {
        deleteUserIdUseCase()
        userPrefsUseCase.markUserAsLoggedOut()
        mainViewModel.sendEvent(MainEvent.OnLogout)
    }

    suspend fun onRefreshTokenFailed(exception: Exception) {
        // Handle refresh token failure, e.g., log out the user
    }
}
