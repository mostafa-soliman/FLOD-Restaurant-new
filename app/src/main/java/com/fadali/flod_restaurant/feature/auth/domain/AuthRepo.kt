package com.fadali.flod_restaurant.feature.auth.domain

import com.fadali.flod_restaurant.feature.auth.domain.model.LoginRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginResponseDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.SignUpRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.SignUpResponseDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.otp.ValidateOtpRequest
import com.fadali.flod_restaurant.feature.auth.domain.model.otp.ValidateOtpResponse

interface AuthRepo {
    suspend fun login(request: LoginRequestDomainModel): LoginResponseDomainModel
    suspend fun signUp(request: SignUpRequestDomainModel): SignUpResponseDomainModel
    suspend fun validateOtp(request: ValidateOtpRequest): ValidateOtpResponse
}
