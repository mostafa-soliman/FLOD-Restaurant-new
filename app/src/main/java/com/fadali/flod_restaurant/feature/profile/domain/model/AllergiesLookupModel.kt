package com.fadali.flod_restaurant.feature.profile.domain.model

data class AllergiesLookupModel(
    val items: List<AllergyData>,
    val totalCount: Int,
)

data class AllergyData(
    val id: String,
    val displayName: String,
    val imageUrl: String,
)
