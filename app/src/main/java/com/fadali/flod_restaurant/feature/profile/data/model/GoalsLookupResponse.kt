package com.fadali.flod_restaurant.feature.profile.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GoalsLookupResponse(
    val items: List<GoalDataModel>,
    val totalCount: Int,
)

@Keep
@Serializable
data class GoalDataModel(
    val id: String,
    val displayName: String,
    val imageUrl: String,
)
