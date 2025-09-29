package com.fadali.flod_restaurant.feature.auth.data.mapper

import com.fadali.flod_restaurant.feature.auth.data.model.SignUpRequestDataModel
import com.fadali.flod_restaurant.feature.auth.data.model.SignUpResponseDataModel
import com.fadali.flod_restaurant.feature.auth.domain.model.SignUpRequestDomainModel
import com.fadali.flod_restaurant.feature.auth.domain.model.SignUpResponseDomainModel

fun SignUpRequestDomainModel.toDataModel() = SignUpRequestDataModel(
    name = name,
    email = email,
    phoneNumber = phoneNumber,
    gender = gender.value,
    dateOfBirth = dateOfBirth
)

fun SignUpResponseDataModel.toDomainModel() = SignUpResponseDomainModel(
    phoneNumber = phoneNumber
)
