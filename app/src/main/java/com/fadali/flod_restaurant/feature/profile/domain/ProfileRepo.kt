package com.fadali.flod_restaurant.feature.profile.domain

import com.fadali.flod_restaurant.feature.profile.domain.model.ActivitiesLookupResponse
import com.fadali.flod_restaurant.feature.profile.domain.model.CompleteUserDataRequest
import com.fadali.flod_restaurant.feature.profile.domain.model.GoalsLookupResponse

interface ProfileRepo {
    suspend fun getActivitiesLookup(): ActivitiesLookupResponse
    suspend fun getGoalsLookup(): GoalsLookupResponse
    suspend fun completeUserData(request: CompleteUserDataRequest): Boolean
}
