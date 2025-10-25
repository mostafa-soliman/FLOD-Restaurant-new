package com.fadali.flod_restaurant.feature.plan.data.datasource.remote

import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.network.ResponseModel
import com.fadali.flod_restaurant.feature.plan.domain.model.GetSubscriptionListRequest
import com.fadali.flod_restaurant.feature.plan.domain.model.PackageDetailsResponse
import com.fadali.flod_restaurant.feature.plan.domain.model.SubscriptionListResponse
import com.smyvo.core.network.NetworkHandler
import com.smyvo.core.utils.filterNotNull

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

    override suspend fun getSubscriptionList(request: GetSubscriptionListRequest): SubscriptionListResponse {
        val url = "${BuildConfig.BASE_URL}${PlanEndpoints.ENDPOINT_GET_SUBSCRIPTION_LIST}"

        val queryParams = mapOf(
            KEY_QUERY_PARAM_SUBSCRIPTION_STATUS to request.subscriptionStatus?.toString(),
            KEY_QUERY_PARAM_PAYMENT_METHOD to request.paymentMethod?.toString(),
            KEY_QUERY_PARAM_PACKAGE_ID to request.packageId,
            KEY_QUERY_PARAM_SKIP to request.skip.toString(),
            KEY_QUERY_PARAM_ROW_COUNT to request.rowCount.toString()
        ).filterNotNull<String, String>()

        val response = networkHandler.get<ResponseModel<SubscriptionListResponse>>(
            url = url,
            queryParams = queryParams
        )
        return response.data!!
    }

    private companion object {
        const val KEY_QUERY_PARAM_SUBSCRIPTION_STATUS = "SubscriptionStatus"
        const val KEY_QUERY_PARAM_PAYMENT_METHOD = "PaymentMethod"
        const val KEY_QUERY_PARAM_PACKAGE_ID = "PackageId"
        const val KEY_QUERY_PARAM_SKIP = "Skip"
        const val KEY_QUERY_PARAM_ROW_COUNT = "RowCount"
    }
}
