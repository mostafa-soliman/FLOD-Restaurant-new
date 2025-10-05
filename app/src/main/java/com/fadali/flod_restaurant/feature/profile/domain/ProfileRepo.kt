package com.fadali.flod_restaurant.feature.profile.domain

import com.fadali.flod_restaurant.feature.profile.domain.model.ActivitiesLookupModel
import com.fadali.flod_restaurant.feature.profile.domain.model.AllergiesLookupModel
import com.fadali.flod_restaurant.feature.profile.domain.model.CompleteUserDataRequest
import com.fadali.flod_restaurant.feature.profile.domain.model.GoalsLookupModel
import com.fadali.flod_restaurant.feature.profile.domain.model.UpdateUserAllergiesRequest

interface ProfileRepo {
    suspend fun getActivitiesLookup(): ActivitiesLookupModel
    suspend fun getGoalsLookup(): GoalsLookupModel
    suspend fun getAllergiesLookup(query: String? = null): AllergiesLookupModel
    suspend fun completeUserData(request: CompleteUserDataRequest): Boolean
    suspend fun updateUserAllergies(request: UpdateUserAllergiesRequest): Boolean
}
