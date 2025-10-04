package com.fadali.flod_restaurant.feature.profile.data.remote

import com.fadali.flod_restaurant.feature.profile.data.model.ActivitiesLookupResponseDataModel
import com.fadali.flod_restaurant.feature.profile.data.model.GoalsLookupResponseDataModel
import com.fadali.flod_restaurant.feature.profile.domain.model.CompleteUserDataRequest

interface ProfileRemoteDataSource {
    suspend fun getActivitiesLookup(): ActivitiesLookupResponseDataModel
    suspend fun getGoalsLookup(): GoalsLookupResponseDataModel
    suspend fun completeUserData(request: CompleteUserDataRequest): Boolean
}
