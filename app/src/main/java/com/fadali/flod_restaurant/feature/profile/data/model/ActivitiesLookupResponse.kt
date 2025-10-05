package com.fadali.flod_restaurant.feature.profile.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ActivitiesLookupResponse(
    val items: List<ActivityDataModel>,
    val totalCount: Int,
)

@Keep
@Serializable
data class ActivityDataModel(
    val id: String,
    val displayName: String,
    val imageUrl: String,
)
