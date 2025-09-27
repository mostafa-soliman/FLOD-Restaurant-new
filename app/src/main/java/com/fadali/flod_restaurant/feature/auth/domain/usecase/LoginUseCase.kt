package com.fadali.flod_restaurant.feature.auth.domain.usecase

import com.fadali.flod_restaurant.feature.auth.domain.AuthRepo
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginResponseDomainModel

class LoginUseCase(
    private val repo: AuthRepo
) {
    suspend operator fun invoke(request: LoginRequestDomainModel): LoginResponseDomainModel {
        return repo.login(request)
    }
}
