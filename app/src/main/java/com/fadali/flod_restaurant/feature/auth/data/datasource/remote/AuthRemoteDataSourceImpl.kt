package com.fadali.flod_restaurant.feature.auth.data.datasource.remote

import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.network.ResponseModel
import com.fadali.flod_restaurant.feature.auth.data.model.LoginRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.LoginResponseDataModel
import com.smyvo.core.network.NetworkHandler

class AuthRemoteDataSourceImpl(
    private val networkHandler: NetworkHandler
) : AuthRemoteDataSource {
    override suspend fun login(request: LoginRequestDataModel): LoginResponseDataModel {
        val url = "${BuildConfig.BASE_URL}api/MobileAccount/Login"
        val response = networkHandler.post<ResponseModel<LoginResponseDataModel>>(
            url = url,
            data = request
        )
        return response.data!!
    }
}
