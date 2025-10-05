package com.fadali.flod_restaurant.feature.profile.data.remote

import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.network.ResponseModel
import com.fadali.flod_restaurant.feature.profile.data.ProfileEndpoints
import com.fadali.flod_restaurant.feature.profile.data.model.ActivitiesLookupResponse
import com.fadali.flod_restaurant.feature.profile.data.model.AllergiesLookupResponse
import com.fadali.flod_restaurant.feature.profile.data.model.GoalsLookupResponse
import com.fadali.flod_restaurant.feature.profile.domain.model.CompleteUserDataRequest
import com.fadali.flod_restaurant.feature.profile.domain.model.UpdateUserAllergiesRequest
import com.smyvo.core.network.NetworkHandler

class ProfileRemoteDataSourceImpl(
    private val networkHandler: NetworkHandler
) : ProfileRemoteDataSource {
    override suspend fun getActivitiesLookup(): ActivitiesLookupResponse {
        val url = "${BuildConfig.BASE_URL}${ProfileEndpoints.ENDPOINT_GET_ACTIVITIES_LOOKUP}"
        val response = networkHandler.get<ResponseModel<ActivitiesLookupResponse>>(
            url = url,
            queryParams = mapOf(
                KEY_ROW_COUNT to "100",
            )
        )
        return response.data!!
    }

    override suspend fun getGoalsLookup(): GoalsLookupResponse {
        val url = "${BuildConfig.BASE_URL}${ProfileEndpoints.ENDPOINT_GET_GOALS_LOOKUP}"
        val response = networkHandler.get<ResponseModel<GoalsLookupResponse>>(
            url = url,
            queryParams = mapOf(
                KEY_ROW_COUNT to "100",
            )
        )
        return response.data!!
    }

    override suspend fun getAllergiesLookup(query: String?): AllergiesLookupResponse {
        val url = "${BuildConfig.BASE_URL}${ProfileEndpoints.ENDPOINT_GET_ALLERGIES_LOOKUP}"
        val queryParams = mutableMapOf(
            KEY_ROW_COUNT to "100",
        )
        if (!query.isNullOrBlank()) {
            queryParams[KEY_FILTER] = query
        }

        val response = networkHandler.get<ResponseModel<AllergiesLookupResponse>>(
            url = url,
            queryParams = queryParams
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

    override suspend fun updateUserAllergies(request: UpdateUserAllergiesRequest): Boolean {
        val url = "${BuildConfig.BASE_URL}${ProfileEndpoints.ENDPOINT_UPDATE_USER_ALLERGIES}"
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
