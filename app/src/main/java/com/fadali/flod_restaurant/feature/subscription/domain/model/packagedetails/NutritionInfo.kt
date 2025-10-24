package com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class NutritionInfo(
    val id: String?,
    val title: String?,
    val calories: Int?,
    val protein: Int?,
    val carbohydrates: Int?,
    val fats: Int?
)
