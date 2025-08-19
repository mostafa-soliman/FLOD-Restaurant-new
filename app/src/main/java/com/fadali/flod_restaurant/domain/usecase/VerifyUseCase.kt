package com.fadali.flod_restaurant.domain.usecase

import com.fadali.flod_restaurant.data.model.auth.VerifyRequest
import com.fadali.flod_restaurant.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(request: VerifyRequest) =
        repo.verifyAccount(request)
}