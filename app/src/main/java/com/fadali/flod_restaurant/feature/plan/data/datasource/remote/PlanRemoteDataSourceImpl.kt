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
            KEY_QUERY_PARAM_SUBSCRIPTION_STATUS to (request.subscriptionStatus?.toString() ?: ""),
            KEY_QUERY_PARAM_PAYMENT_METHOD to (request.paymentMethod?.toString() ?: ""),
            KEY_QUERY_PARAM_PACKAGE_ID to (request.packageId ?: ""),
            KEY_QUERY_PARAM_SKIP to request.skip.toString(),
            KEY_QUERY_PARAM_ROW_COUNT to request.rowCount.toString(),
            KEY_QUERY_PARAM_START_DATE_FROM to (request.startDateFrom ?: ""),
            KEY_QUERY_PARAM_START_DATE_TO to (request.startDateTo ?: ""),
            KEY_QUERY_PARAM_END_DATE_FROM to (request.endDateFrom ?: ""),
            KEY_QUERY_PARAM_END_DATE_TO to (request.endDateTo ?: ""),
            KEY_QUERY_PARAM_CREATE_DATE_FROM to (request.createDateFrom ?: ""),
            KEY_QUERY_PARAM_CREATE_DATE_TO to (request.createDateTo ?: ""),
            KEY_QUERY_PARAM_SORTING to (request.sorting ?: ""),
            KEY_QUERY_PARAM_SORTING_TYPE to (request.sortingType ?: "")
        )

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
        const val KEY_QUERY_PARAM_START_DATE_FROM = "StartDateFrom"
        const val KEY_QUERY_PARAM_START_DATE_TO = "StartDateTo"
        const val KEY_QUERY_PARAM_END_DATE_FROM = "EndDateFrom"
        const val KEY_QUERY_PARAM_END_DATE_TO = "EndDateTo"
        const val KEY_QUERY_PARAM_CREATE_DATE_FROM = "CreateDateFrom"
        const val KEY_QUERY_PARAM_CREATE_DATE_TO = "CreateDateTo"
        const val KEY_QUERY_PARAM_SORTING = "Sorting"
        const val KEY_QUERY_PARAM_SORTING_TYPE = "SortingType"
    }
}
