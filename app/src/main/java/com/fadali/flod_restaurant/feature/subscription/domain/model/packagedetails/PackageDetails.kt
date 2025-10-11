package com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PackageDetails(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val durationDays: Int,
    val mealsPerDay: Int,
    val imageUrl: String? = null, // Fixme: It's not in the API response
    val branches: List<PackageDetailsBranch>
)

@Keep
@Serializable
data class PackageDetailsBranch(
    val id: String,
    val title: String,
    val deliveryArea: String,
)
