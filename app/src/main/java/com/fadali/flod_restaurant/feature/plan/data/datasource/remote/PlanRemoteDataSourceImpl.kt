package com.fadali.flod_restaurant.feature.plan.data.datasource.remote

import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.network.ResponseModel
import com.fadali.flod_restaurant.feature.plan.domain.model.PackageDetailsResponse
import com.smyvo.core.network.NetworkHandler

class PlanRemoteDataSourceImpl(
    private val networkHandler: NetworkHandler
) : PlanRemoteDataSource {

    override suspend fun getPackageDetails(subscriptionId: String): PackageDetailsResponse {
        val url =
            "${BuildConfig.BASE_URL}${PlanEndpoints.ENDPOINT_GET_SUBSCRIPTION_DETAILS}/$subscriptionId"

        val response = networkHandler.get<ResponseModel<PackageDetailsResponse>>(
            url = url
        )
        return response.data!!
    }

}
