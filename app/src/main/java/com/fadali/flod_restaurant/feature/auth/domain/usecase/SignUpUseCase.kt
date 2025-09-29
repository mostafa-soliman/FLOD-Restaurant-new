package com.fadali.flod_restaurant.feature.auth.domain.usecase

import com.fadali.flod_restaurant.feature.auth.domain.AuthRepo
import com.fadali.flod_restaurant.feature.auth.domain.model.SignUpRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.SignUpResponseDomainModel

class SignUpUseCase(
    private val repo: AuthRepo
) {
    suspend operator fun invoke(request: SignUpRequestDomainModel): SignUpResponseDomainModel {
        return repo.signUp(request)
    }
}
