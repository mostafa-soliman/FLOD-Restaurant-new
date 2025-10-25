package com.fadali.flod_restaurant.feature.plan.domain

import com.fadali.flod_restaurant.feature.plan.domain.model.GetSubscriptionListRequest
import com.fadali.flod_restaurant.feature.plan.domain.model.PackageDetailsResponse
import com.fadali.flod_restaurant.feature.plan.domain.model.SubscriptionListResponse

interface PlanRepo {
    suspend fun getSubscriptionDetails(subscriptionId: String): PackageDetailsResponse
    suspend fun getSubscriptionList(request: GetSubscriptionListRequest): SubscriptionListResponse
}
