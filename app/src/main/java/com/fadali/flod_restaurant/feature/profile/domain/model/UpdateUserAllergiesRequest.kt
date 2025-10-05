package com.fadali.flod_restaurant.feature.profile.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UpdateUserAllergiesRequest(
    val allergyIds: List<String>,
)
