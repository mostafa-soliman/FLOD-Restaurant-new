package com.fadali.flod_restaurant.feature.profile.data.remote

import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.network.ResponseModel
import com.fadali.flod_restaurant.feature.profile.data.ProfileEndpoints
import com.fadali.flod_restaurant.feature.profile.data.model.ActivitiesLookupResponseDataModel
import com.fadali.flod_restaurant.feature.profile.data.model.GoalsLookupResponseDataModel
import com.fadali.flod_restaurant.feature.profile.domain.model.CompleteUserDataRequest
import com.smyvo.core.network.NetworkHandler

class ProfileRemoteDataSourceImpl(
    private val networkHandler: NetworkHandler
) : ProfileRemoteDataSource {
    override suspend fun getActivitiesLookup(): ActivitiesLookupResponseDataModel {
        val url = "${BuildConfig.BASE_URL}${ProfileEndpoints.ENDPOINT_GET_ACTIVITIES_LOOKUP}"
        val response = networkHandler.get<ResponseModel<ActivitiesLookupResponseDataModel>>(
            url = url,
            queryParams = mapOf(
                KEY_ROW_COUNT to "100",
            )
        )
        return response.data!!
    }

    override suspend fun getGoalsLookup(): GoalsLookupResponseDataModel {
        val url = "${BuildConfig.BASE_URL}${ProfileEndpoints.ENDPOINT_GET_GOALS_LOOKUP}"
        val response = networkHandler.get<ResponseModel<GoalsLookupResponseDataModel>>(
            url = url,
            queryParams = mapOf(
                KEY_ROW_COUNT to "100",
            )
        )
        return response.data!!
    }

    override suspend fun completeUserData(request: CompleteUserDataRequest): Boolean {
        val url = "${BuildConfig.BASE_URL}${ProfileEndpoints.ENDPOINT_COMPLETE_USER_DATA}"
        val response = networkHandler.put<ResponseModel<Boolean>>(
            url = url,
            data = request
        )
        return response.data!!
    }


    private companion object {
        const val KEY_FILTER = "FilterText"
        const val KEY_SORTING = "Sorting"
        const val KEY_SORTING_TYPE = "SortingType"
        const val KEY_ROW_COUNT = "RowCount"
        const val KEY_SKIP = "Skip"
    }
}
