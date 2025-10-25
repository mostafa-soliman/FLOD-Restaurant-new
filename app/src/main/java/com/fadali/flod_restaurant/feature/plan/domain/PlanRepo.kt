package com.fadali.flod_restaurant.feature.plan.domain

import com.fadali.flod_restaurant.feature.plan.domain.model.PackageDetailsResponse

interface PlanRepo {
    suspend fun getSubscriptionDetails(subscriptionId: String): PackageDetailsResponse
}
