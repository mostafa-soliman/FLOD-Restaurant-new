package com.fadali.flod_restaurant.data.repository

import com.fadali.flod_restaurant.data.model.auth.LoginRequest
import com.fadali.flod_restaurant.data.model.auth.LoginResponse
import com.fadali.flod_restaurant.data.model.auth.SignUpRequest
import com.fadali.flod_restaurant.data.model.remote.ApiService
import com.fadali.flod_restaurant.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService
) : AuthRepository {

    override suspend fun login(mobile: String): Response<LoginResponse> {
        return api.login(LoginRequest(mobile))
    }

    override suspend fun signUp(request: SignUpRequest) =
        api.signUp(request)
}

