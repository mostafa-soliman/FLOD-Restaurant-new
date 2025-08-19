package com.fadali.flod_restaurant.domain.usecase

import com.fadali.flod_restaurant.data.model.auth.SignUpRequest
import com.fadali.flod_restaurant.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(request: SignUpRequest) =
        repo.signUp(request)
}
