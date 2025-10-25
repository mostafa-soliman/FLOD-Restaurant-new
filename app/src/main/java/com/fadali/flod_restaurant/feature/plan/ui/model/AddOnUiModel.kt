package com.fadali.flod_restaurant.feature.plan.ui.model

data class AddOnUiModel(
    val id: String,
    val imageUrl: Any,
    val name: String,
    val calories: Int?,
    val price: Double,
    val quantity: Int = 0
)

