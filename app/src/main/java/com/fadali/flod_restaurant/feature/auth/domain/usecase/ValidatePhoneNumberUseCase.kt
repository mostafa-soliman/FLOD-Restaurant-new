package com.fadali.flod_restaurant.feature.auth.domain.usecase

class ValidatePhoneNumberUseCase {
    operator fun invoke(phoneNumber: String): Boolean {
        val finalNumber = if (phoneNumber.startsWith("0")) {
            phoneNumber
        } else {
            "0$phoneNumber"
        }
        val regex = Regex("^05[0-9]{8}$")
        return regex.matches(finalNumber)
    }
}
