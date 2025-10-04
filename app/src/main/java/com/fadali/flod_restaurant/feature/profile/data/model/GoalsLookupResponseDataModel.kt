package com.fadali.flod_restaurant.feature.profile.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GoalsLookupResponseDataModel(
    val items: List<GoalDataDataModel>,
    val totalCount: Int,
)

@Keep
@Serializable
data class GoalDataDataModel(
    val id: String,
    val displayName: String,
    val imageUrl: String,
)
