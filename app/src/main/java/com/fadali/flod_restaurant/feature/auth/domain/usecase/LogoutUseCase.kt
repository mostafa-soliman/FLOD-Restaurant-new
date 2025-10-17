package com.fadali.flod_restaurant.feature.auth.domain.usecase

import com.fadali.flod_restaurant.feature.auth.domain.AuthRepo

class LogoutUseCase(
    private val repo: AuthRepo
) {
    suspend operator fun invoke(): Boolean {
        return repo.logout()
    }
}
