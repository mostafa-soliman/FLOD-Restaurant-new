package com.fadali.flod_restaurant.feature.auth.domain

import com.fadali.flod_restaurant.feature.auth.domain.model.LoginRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginResponseDomainModel

interface AuthRepo {
    suspend fun login(request: LoginRequestDomainModel): LoginResponseDomainModel
}
