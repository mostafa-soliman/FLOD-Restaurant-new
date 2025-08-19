package com.fadali.flod_restaurant.domain.repository

import com.fadali.flod_restaurant.data.model.auth.LoginResponse
import com.fadali.flod_restaurant.data.model.auth.SignUpRequest
import com.fadali.flod_restaurant.data.model.auth.SignUpResponse
import com.fadali.flod_restaurant.data.model.auth.VerifyRequest
import com.fadali.flod_restaurant.data.model.auth.VerifyResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun login(mobile: String): Response<LoginResponse>
    suspend fun signUp(request: SignUpRequest): Response<SignUpResponse>

    suspend fun verifyAccount(request: VerifyRequest): Response<VerifyResponse>
}
