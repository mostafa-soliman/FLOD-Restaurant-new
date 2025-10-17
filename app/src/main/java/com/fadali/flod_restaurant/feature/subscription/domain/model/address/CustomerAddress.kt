package com.fadali.flod_restaurant.feature.subscription.domain.model.address

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CustomerAddress(
    val id: String,
    val addressType: Int,
    val streetName: String,
    val buildingName: String,
    val flatNumber: Int,
    val city: String,
    val district: String,
    val landmark: String,
    val additionalNotes: String?,
    val displayName: String = "$city, $district, $streetName",
    val latitude: Double,
    val longitude: Double,
) {
    companion object {
        val empty = CustomerAddress(
            id = "",
            addressType = 0,
            streetName = "",
            buildingName = "",
            flatNumber = 0,
            city = "",
            district = "",
            landmark = "",
            additionalNotes = "",
            displayName = "",
            latitude = 0.0,
            longitude = 0.0,
        )
    }
}
