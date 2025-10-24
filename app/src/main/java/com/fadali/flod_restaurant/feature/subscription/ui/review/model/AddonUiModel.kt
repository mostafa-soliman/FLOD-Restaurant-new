package com.fadali.flod_restaurant.feature.subscription.ui.review.model

import com.fadali.flod_restaurant.feature.subscription.domain.model.addons.AddonNutritionInfo

data class AddonUiModel(
    val id: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String,
    val nutritionInfo: AddonNutritionInfo?
)
