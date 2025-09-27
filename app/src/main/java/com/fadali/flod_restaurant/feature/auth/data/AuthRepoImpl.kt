package com.fadali.flod_restaurant.feature.auth.data

import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthRemoteDataSource
import com.fadali.flod_restaurant.feature.auth.data.mapper.toDataModel
import com.fadali.flod_restaurant.feature.auth.data.mapper.toDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.AuthRepo
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginResponseDomainModel

class AuthRepoImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepo {
    override suspend fun login(request: LoginRequestDomainModel): LoginResponseDomainModel {
        return authRemoteDataSource.login(request = request.toDataModel()).toDomainModel()
    }
}
