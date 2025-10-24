package com.fadali.flod_restaurant.feature.subscription.domain.model.promocode

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PromoCode(
    val id: String,
    val code: String,
    val discountType: Int,
    val discountValue: Double,
    val isActive: Boolean,
    val usageCount: Int,
    val maxNumberOfUses: Int,
    val maxNumberOfUsesPerUser: Int,
    val minOrderAmount: Double,
    val canCombineWithOtherOffers: Boolean,
)
