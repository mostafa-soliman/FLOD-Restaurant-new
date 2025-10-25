package com.fadali.flod_restaurant.feature.plan.data.datasource.remote

import com.fadali.flod_restaurant.feature.plan.domain.model.PackageDetailsResponse

interface PlanRemoteDataSource {
    suspend fun getPackageDetails(subscriptionId: String): PackageDetailsResponse
}
