package com.fadali.flod_restaurant.feature.subscription.domain.model.timeslots

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class DeliveryTimeSlot(
    val id: String,
    val startTime: String,
    val endTime: String,
    val timeSlotType: Int,
)
