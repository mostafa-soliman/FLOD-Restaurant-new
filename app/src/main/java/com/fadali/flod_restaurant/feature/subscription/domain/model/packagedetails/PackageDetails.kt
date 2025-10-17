package com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PackageDetails(
    val id: String,
    val title: String,
    val description: String?,
    val price: Double,
    val durationDays: Int,
    val mealsPerDay: Int,
    val imageUrl: String?,
    val nutritionInfo: NutritionInfo,
    val branches: List<PackageDetailsBranch>
)

@Keep
@Serializable
data class PackageDetailsBranch(
    val id: String,
    val title: String,
    val deliveryArea: String,
)

@Keep
@Serializable
data class NutritionInfo(
    val id: String,
    val title: String,
    val calories: Int,
    val protein: Double,
    val carbohydrates: Double,
    val fats: Double,
)
