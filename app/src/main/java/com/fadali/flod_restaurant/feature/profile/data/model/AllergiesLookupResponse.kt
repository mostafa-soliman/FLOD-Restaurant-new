package com.fadali.flod_restaurant.feature.profile.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AllergiesLookupResponse(
    val items: List<AllergiesDataModel>,
    val totalCount: Int,
)

@Keep
@Serializable
data class AllergiesDataModel(
    val id: String,
    val displayName: String,
    val imageUrl: String,
)
