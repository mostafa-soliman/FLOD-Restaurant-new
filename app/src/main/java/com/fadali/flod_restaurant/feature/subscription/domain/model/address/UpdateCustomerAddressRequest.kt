package com.fadali.flod_restaurant.feature.subscription.domain.model.address

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UpdateCustomerAddressRequest(
    val id: String,
    val addressType: Int,
    val streetName: String,
    val buildingName: String,
    val flatNumber: Int,
    val city: String,
    val district: String,
    val landmark: String,
    val additionalNotes: String?,
    val latitude: Double,
    val longitude: Double,
)
