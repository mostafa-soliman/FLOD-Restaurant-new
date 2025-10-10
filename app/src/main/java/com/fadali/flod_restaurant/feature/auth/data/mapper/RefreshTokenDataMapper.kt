package com.fadali.flod_restaurant.feature.auth.data.mapper

import com.fadali.flod_restaurant.feature.auth.data.model.refreshtoken.RefreshTokenRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.refreshtoken.RefreshTokenResponseDataModel
import com.fadali.flod_restaurant.feature.auth.domain.model.refreshtoken.RefreshTokenRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.refreshtoken.RefreshTokenResponseDomainModel

fun RefreshTokenRequestDomainModel.toDataModel() = RefreshTokenRequestDataModel(
    userId = userId,
    refreshToken = refreshToken,
)

fun RefreshTokenResponseDataModel.toDomainModel() = RefreshTokenResponseDomainModel(
    accessToken = accessToken,
    refreshToken = refreshToken,
    needPasswordChange = needPasswordChange
)
