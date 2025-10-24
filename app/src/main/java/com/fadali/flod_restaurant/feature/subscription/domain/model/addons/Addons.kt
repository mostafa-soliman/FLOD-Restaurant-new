package com.fadali.flod_restaurant.feature.subscription.domain.model.addons

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AddonsResponse(
    val items: List<Addon>
)

@Keep
@Serializable
data class Addon(
    val id: String,
    val titleAr: String,
    val titleEn: String,
    val price: Double,
    val imageUrl: String,
    val nutritionInfo: AddonNutritionInfo?,
)

@Keep
@Serializable
data class AddonNutritionInfo(
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
)
