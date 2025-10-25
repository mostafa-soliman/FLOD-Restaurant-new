package com.fadali.flod_restaurant.feature.plan.data.datasource.remote

import com.fadali.flod_restaurant.feature.plan.domain.model.GetSubscriptionListRequest
import com.fadali.flod_restaurant.feature.plan.domain.model.PackageDetailsResponse
import com.fadali.flod_restaurant.feature.plan.domain.model.SubscriptionListResponse

interface PlanRemoteDataSource {
    suspend fun getPackageDetails(subscriptionId: String): PackageDetailsResponse
    suspend fun getSubscriptionList(request: GetSubscriptionListRequest): SubscriptionListResponse
}
