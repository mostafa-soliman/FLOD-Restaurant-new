package com.fadali.flod_restaurant.feature.plan.ui.myplans.model

data class PlanItem(
    val id: String,
    val imageUrl: String,
    val title: String,
    val planDuration: String,
    val mealsCount: String,
    val startDate: String,
    val endDate: String,
    val isPickup: Boolean,
    val status: PlanStatus
)

enum class PlanStatus {
    ACTIVE,
    EXPIRED,
    CANCELLED
}
