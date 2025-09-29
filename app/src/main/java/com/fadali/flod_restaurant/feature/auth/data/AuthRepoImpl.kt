package com.fadali.flod_restaurant.feature.auth.data

import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthRemoteDataSource
import com.fadali.flod_restaurant.feature.auth.data.mapper.toDataModel
import com.fadali.flod_restaurant.feature.auth.data.mapper.toDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.AuthRepo
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginResponseDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.SignUpRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.SignUpResponseDomainModel

class AuthRepoImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepo {
    override suspend fun login(request: LoginRequestDomainModel): LoginResponseDomainModel {
        return authRemoteDataSource.login(request = request.toDataModel()).toDomainModel()
    }

    override suspend fun signUp(request: SignUpRequestDomainModel): SignUpResponseDomainModel {
        return authRemoteDataSource.signUp(request = request.toDataModel()).toDomainModel()
    }
}
