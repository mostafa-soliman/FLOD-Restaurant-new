package com.fadali.flod_restaurant.feature.auth.data.datasource.remote

import com.fadali.flod_restaurant.feature.auth.data.model.LoginRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.LoginResponseDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.SignUpRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.SignUpResponseDataModel

interface AuthRemoteDataSource {
    suspend fun login(request: LoginRequestDataModel): LoginResponseDataModel
    suspend fun signUp(request: SignUpRequestDataModel): SignUpResponseDataModel
}
