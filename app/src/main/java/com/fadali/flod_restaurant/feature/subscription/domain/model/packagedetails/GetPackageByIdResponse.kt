package com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GetPackageByIdResponse(
    val id: String?,
    val title: String?,
    val description: String?,
    val price: Double?,
    val durationDays: Int?,
    val mealsPerDay: Int?,
    val imageUrl: String?,
    val packageType: Int?,
    val expirationDate: String?,
    val gracePeriodDays: Int?,
    val isActive: Boolean?,
    val category: String?,
    val nutritionInfo: NutritionInfo?,
    val meals: List<PackageMeal>?
)

@Keep
@Serializable
data class PackageMeal(
    val id: String?,
    val title: String?,
    val imageUrl: String?,
    val price: Double?,
    val serialDay: Int?,
    val orderPerDay: Int?
)