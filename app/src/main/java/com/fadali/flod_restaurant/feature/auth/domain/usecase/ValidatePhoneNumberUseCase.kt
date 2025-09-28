package com.fadali.flod_restaurant.feature.auth.domain.usecase

class ValidatePhoneNumberUseCase {
    operator fun invoke(phoneNumber: String): String? {
        val finalNumber = if (phoneNumber.startsWith("0")) {
            phoneNumber
        } else {
            "0$phoneNumber"
        }
        val regex = Regex("^05[0-9]{8}$")
        val isValidNumber = regex.matches(finalNumber)
        return if (isValidNumber) finalNumber else null
    }
}
