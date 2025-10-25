package com.fadali.flod_restaurant.feature.plan.data

import com.fadali.flod_restaurant.feature.plan.data.datasource.remote.PlanRemoteDataSource
import com.fadali.flod_restaurant.feature.plan.domain.PlanRepo
import com.fadali.flod_restaurant.feature.plan.domain.model.GetSubscriptionListRequest
import com.fadali.flod_restaurant.feature.plan.domain.model.PackageDetailsResponse
import com.fadali.flod_restaurant.feature.plan.domain.model.SubscriptionListResponse

class PlanRepoImpl(
    private val remoteDataSource: PlanRemoteDataSource
) : PlanRepo {

    override suspend fun getSubscriptionDetails(subscriptionId: String): PackageDetailsResponse {
        return remoteDataSource.getPackageDetails(subscriptionId)
    }

    override suspend fun getSubscriptionList(request: GetSubscriptionListRequest): SubscriptionListResponse {
        return remoteDataSource.getSubscriptionList(request)
    }
}
