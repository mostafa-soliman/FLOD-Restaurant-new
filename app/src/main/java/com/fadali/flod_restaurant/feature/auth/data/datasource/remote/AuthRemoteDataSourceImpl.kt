package com.fadali.flod_restaurant.feature.auth.data.datasource.remote

import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.network.ResponseModel
import com.fadali.flod_restaurant.feature.auth.data.model.LoginRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.LoginResponseDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.SignUpRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.SignUpResponseDataModel
import com.smyvo.core.network.NetworkHandler

class AuthRemoteDataSourceImpl(
    private val networkHandler: NetworkHandler
) : AuthRemoteDataSource {
    override suspend fun login(request: LoginRequestDataModel): LoginResponseDataModel {
        val url = "${BuildConfig.BASE_URL}${AuthEndPoints.ENDPOINT_LOGIN}"
        val response = networkHandler.post<ResponseModel<LoginResponseDataModel>>(
            url = url,
            data = request
        )
        return response.data!!
    }

    override suspend fun signUp(request: SignUpRequestDataModel): SignUpResponseDataModel {
        val url = "${BuildConfig.BASE_URL}${AuthEndPoints.ENDPOINT_SIGN_UP}"
        val response = networkHandler.post<ResponseModel<String>>(
            url = url,
            data = request
        )
        return SignUpResponseDataModel(phoneNumber = response.data!!)
    }
}
