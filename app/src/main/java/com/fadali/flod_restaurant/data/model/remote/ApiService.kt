package com.fadali.flod_restaurant.data.model.remote

import com.fadali.flod_restaurant.data.model.auth.LoginRequest
import com.fadali.flod_restaurant.data.model.auth.LoginResponse
import com.fadali.flod_restaurant.data.model.auth.SignUpRequest
import com.fadali.flod_restaurant.data.model.auth.SignUpResponse
import com.fadali.flod_restaurant.data.model.auth.VerifyRequest
import com.fadali.flod_restaurant.data.model.auth.VerifyResponse
import com.fadali.flod_restaurant.util.Constants.Companion.SUB_DOMAIN
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("${SUB_DOMAIN}Auth/Login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("${SUB_DOMAIN}Auth/SignUp")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("api/Auth/VerifyAccount")
    suspend fun verifyAccount(
        @Body request: VerifyRequest
    ): Response<VerifyResponse>

}
