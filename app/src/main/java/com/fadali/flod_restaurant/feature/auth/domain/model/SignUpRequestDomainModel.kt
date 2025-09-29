package com.fadali.flod_restaurant.feature.auth.domain.model

data class SignUpRequestDomainModel(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val gender: Gender,
    val dateOfBirth: String,
)
