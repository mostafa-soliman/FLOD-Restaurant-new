package com.fadali.flod_restaurant.data.model.remote

import com.fadali.flod_restaurant.data.model.auth.LoginRequest
import com.fadali.flod_restaurant.data.model.auth.LoginResponse
import com.fadali.flod_restaurant.util.Constants.Companion.SUB_DOMAIN
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("${SUB_DOMAIN}Auth/Login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}
