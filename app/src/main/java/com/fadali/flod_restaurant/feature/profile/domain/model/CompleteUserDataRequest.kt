package com.fadali.flod_restaurant.feature.profile.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CompleteUserDataRequest(
    val height: Double?,
    val weight: Double?,
    val activityId: String?,
    val goalId: String?,
)
