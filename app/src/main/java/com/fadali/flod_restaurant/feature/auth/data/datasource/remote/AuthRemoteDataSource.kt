package com.fadali.flod_restaurant.feature.auth.data.datasource.remote

import com.fadali.flod_restaurant.feature.auth.data.model.LoginRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.LoginResponseDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.SignUpRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.SignUpResponseDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.otp.ValidateOtpLoginRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.otp.ValidateOtpLoginResponseDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.otp.ValidateOtpSignUpRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.otp.ValidateOtpSignUpResponseDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.refreshtoken.RefreshTokenRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.refreshtoken.RefreshTokenResponseDataModel

interface AuthRemoteDataSource {
    suspend fun login(request: LoginRequestDataModel): LoginResponseDataModel
    suspend fun signUp(request: SignUpRequestDataModel): SignUpResponseDataModel
    suspend fun validateLoginOtp(request: ValidateOtpLoginRequestDataModel): ValidateOtpLoginResponseDataModel
    suspend fun validateSignUpOtp(request: ValidateOtpSignUpRequestDataModel): ValidateOtpSignUpResponseDataModel
    suspend fun refreshToken(request: RefreshTokenRequestDataModel): RefreshTokenResponseDataModel
    suspend fun logout(): Boolean
}
