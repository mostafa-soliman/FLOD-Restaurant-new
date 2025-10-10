package com.fadali.flod_restaurant.feature.auth.data

import com.fadali.flod_restaurant.feature.auth.data.datasource.remote.AuthRemoteDataSource
import com.fadali.flod_restaurant.feature.auth.data.mapper.toDataModel
import com.fadali.flod_restaurant.feature.auth.data.mapper.toDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.AuthRepo
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginResponseDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.SignUpRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.SignUpResponseDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.otp.ValidateOtpRequest
import com.fadali.flod_restaurant.feature.auth.domain.model.otp.ValidateOtpResponse
import com.fadali.flod_restaurant.feature.auth.domain.model.refreshtoken.RefreshTokenRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.refreshtoken.RefreshTokenResponseDomainModel

class AuthRepoImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepo {
    override suspend fun login(request: LoginRequestDomainModel): LoginResponseDomainModel {
        return authRemoteDataSource.login(request = request.toDataModel()).toDomainModel()
    }

    override suspend fun signUp(request: SignUpRequestDomainModel): SignUpResponseDomainModel {
        return authRemoteDataSource.signUp(request = request.toDataModel()).toDomainModel()
    }

    override suspend fun validateOtp(request: ValidateOtpRequest): ValidateOtpResponse {
        return when (request) {
            is ValidateOtpRequest.Login -> {
                authRemoteDataSource.validateLoginOtp(request.toDataModel()).toDomainModel()
            }

            is ValidateOtpRequest.SignUp -> {
                authRemoteDataSource.validateSignUpOtp(request.toDataModel()).toDomainModel()
            }
        }
    }

    override suspend fun refreshToken(request: RefreshTokenRequestDomainModel): RefreshTokenResponseDomainModel {
        return authRemoteDataSource.refreshToken(request = request.toDataModel()).toDomainModel()
    }
}
