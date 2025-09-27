package com.fadali.flod_restaurant.feature.auth.domain.model

data class LoginResponseDomainModel(
    val customerId: String,
    val notRegistered: Boolean
)
