package com.fadali.flod_restaurant.domain.repository

import com.fadali.flod_restaurant.data.model.auth.LoginResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun login(mobile: String): Response<LoginResponse>
}
