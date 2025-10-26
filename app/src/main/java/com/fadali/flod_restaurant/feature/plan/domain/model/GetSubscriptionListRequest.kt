package com.fadali.flod_restaurant.feature.plan.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GetSubscriptionListRequest(
    val subscriptionStatus: Int? = null,
    val paymentMethod: Int? = null,
    val packageId: String? = null,
    val skip: Int = 0,
    val rowCount: Int = 100,
    val startDateFrom: String? = null,
    val startDateTo: String? = null,
    val endDateFrom: String? = null,
    val endDateTo: String? = null,
    val createDateFrom: String? = null,
    val createDateTo: String? = null,
    val sorting: String? = null,
    val sortingType: String? = null,
    )

