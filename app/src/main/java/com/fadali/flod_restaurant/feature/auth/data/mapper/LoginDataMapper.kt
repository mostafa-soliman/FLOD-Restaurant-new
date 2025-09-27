package com.fadali.flod_restaurant.feature.auth.data.mapper

import com.fadali.flod_restaurant.feature.auth.data.model.LoginRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.LoginResponseDataModel
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.LoginResponseDomainModel

fun LoginRequestDomainModel.toDataModel() = LoginRequestDataModel(
    phoneNumber = phoneNumber
)

fun LoginResponseDataModel.toDomainModel() = LoginResponseDomainModel(
    customerId = customerId,
    notRegistered = notRegistered
)
