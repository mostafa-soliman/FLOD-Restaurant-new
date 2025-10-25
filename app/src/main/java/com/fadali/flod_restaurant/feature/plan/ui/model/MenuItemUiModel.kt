package com.fadali.flod_restaurant.feature.plan.ui.model

data class MenuItemUiModel(
    val id: String,
    val imageUrl: Any,
    val title: String,
    val mealNumber: String,
    val calories: Int? = null,
    val protein: String? = null,
    val carbs: String? = null,
    val fat: String? = null,
    val isPickup: Boolean? = null
)
