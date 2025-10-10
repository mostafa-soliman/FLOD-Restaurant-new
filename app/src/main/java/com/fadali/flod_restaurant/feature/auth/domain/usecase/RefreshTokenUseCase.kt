package com.fadali.flod_restaurant.feature.auth.domain.usecase

import com.fadali.flod_restaurant.feature.auth.domain.AuthRepo
import com.fadali.flod_restaurant.feature.auth.domain.model.refreshtoken.RefreshTokenRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.refreshtoken.RefreshTokenResponseDomainModel

class RefreshTokenUseCase(
    private val repo: AuthRepo
) {
    suspend operator fun invoke(
        request: RefreshTokenRequestDomainModel
    ): RefreshTokenResponseDomainModel = repo.refreshToken(request)
}
