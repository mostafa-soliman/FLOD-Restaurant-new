package com.fadali.flod_restaurant.feature.profile.domain.model

data class ActivitiesLookupModel(
    val items: List<ActivityData>,
    val totalCount: Int,
)

data class ActivityData(
    val id: String,
    val displayName: String,
    val imageUrl: String,
)
