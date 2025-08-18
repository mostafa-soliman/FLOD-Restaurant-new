package com.fadali.flod_restaurant.domain.usecase

import com.fadali.flod_restaurant.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(mobile: String) = repo.login(mobile)
}
