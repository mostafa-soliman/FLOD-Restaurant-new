package com.fadali.flod_restaurant.feature.profile.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ActivitiesLookupResponseDataModel(
    val items: List<ActivityDataDataModel>,
    val totalCount: Int,
)

@Keep
@Serializable
data class ActivityDataDataModel(
    val id: String,
    val displayName: String,
    val imageUrl: String,
)
