package com.fadali.flod_restaurant.feature.auth.data.mapper

import com.fadali.flod_restaurant.feature.auth.data.model.otp.ValidateOtpLoginRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.otp.ValidateOtpLoginResponseDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.otp.ValidateOtpSignUpRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.otp.ValidateOtpSignUpResponseDataModel
import com.fadali.flod_restaurant.feature.auth.domain.model.otp.ValidateOtpRequest
import com.fadali.flod_restaurant.feature.auth.domain.model.otp.ValidateOtpResponse

fun ValidateOtpRequest.Login.toDataModel() = ValidateOtpLoginRequestDataModel(
    otp = otp,
    userId = userId,
)

fun ValidateOtpRequest.SignUp.toDataModel() = ValidateOtpSignUpRequestDataModel(
    phoneNumber = phoneNumber,
    otpCode = otpCode,
)

fun ValidateOtpLoginResponseDataModel.toDomainModel() = ValidateOtpResponse(
    accessToken = accessToken,
    refreshToken = refreshToken,
    needPasswordChange = needPasswordChange
)

fun ValidateOtpSignUpResponseDataModel.toDomainModel() = ValidateOtpResponse(
    accessToken = accessToken,
    refreshToken = refreshToken,
    needPasswordChange = needPasswordChange
)
