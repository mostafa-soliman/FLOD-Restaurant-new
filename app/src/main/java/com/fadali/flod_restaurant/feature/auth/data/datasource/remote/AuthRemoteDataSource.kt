package com.fadali.flod_restaurant.feature.auth.data.datasource.remote

import com.fadali.flod_restaurant.feature.auth.data.model.LoginRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.LoginResponseDataModel

interface AuthRemoteDataSource {
    suspend fun login(request: LoginRequestDataModel): LoginResponseDataModel
}
