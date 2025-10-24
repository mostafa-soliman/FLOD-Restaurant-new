package com.fadali.flod_restaurant.feature.subscription.domain.model.payment

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CreateSubscriptionRequest(
    val packageId: String,
    val itemsQuantities: List<SubscriptionItemQuantity>,
    val branchId: String?,
    val customerAddressId: String?,
    val startDate: String,
    val paymentMethod: Int,
    val packageType: Int,
    val promoCodeId: String?,
    val schedules: List<SubscriptionSchedule>,
    val notes: String? = null,
)
