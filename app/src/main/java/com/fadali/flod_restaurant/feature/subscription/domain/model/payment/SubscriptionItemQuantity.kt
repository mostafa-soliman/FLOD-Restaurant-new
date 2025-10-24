package com.fadali.flod_restaurant.feature.subscription.domain.model.payment

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SubscriptionItemQuantity(
    val itemId: String,
    val quantities: Int
)
