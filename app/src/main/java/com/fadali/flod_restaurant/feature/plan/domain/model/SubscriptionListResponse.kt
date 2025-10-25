package com.fadali.flod_restaurant.feature.plan.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SubscriptionListResponse(
    val items: List<SubscriptionListItem>,
    val totalCount: Int
)

@Keep
@Serializable
data class SubscriptionListItem(
    val id: String,
    val subscriptionCode: String?,
    val packageTitle: String?,
    val packageImageUrl: String?,
    val packageDaysDuration: Int?,
    val mealsPerDay: Int?,
    val startDate: String?,
    val endDate: String?,
    val subscriptionStatus: Int?,
    val packageType: Int?,
    val totalPrice: Double?,
    val netPrice: Double?
)

