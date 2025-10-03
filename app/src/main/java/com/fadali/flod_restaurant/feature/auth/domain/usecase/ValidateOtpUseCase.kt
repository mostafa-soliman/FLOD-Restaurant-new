package com.fadali.flod_restaurant.feature.auth.domain.usecase

import com.fadali.flod_restaurant.feature.auth.domain.AuthRepo
import com.fadali.flod_restaurant.feature.auth.domain.model.otp.ValidateOtpRequest
import com.fadali.flod_restaurant.feature.auth.domain.model.otp.ValidateOtpResponse

class ValidateOtpUseCase(
    private val repo: AuthRepo
) {
    suspend operator fun invoke(request: ValidateOtpRequest): ValidateOtpResponse {
        return repo.validateOtp(request)
    }
}
