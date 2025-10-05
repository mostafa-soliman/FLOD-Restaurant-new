package com.fadali.flod_restaurant.feature.profile.domain.model

data class GoalsLookupModel(
    val items: List<GoalData>,
    val totalCount: Int,
)

data class GoalData(
    val id: String,
    val displayName: String,
    val imageUrl: String,
)
