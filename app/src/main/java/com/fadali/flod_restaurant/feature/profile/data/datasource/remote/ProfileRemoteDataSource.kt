package com.fadali.flod_restaurant.feature.profile.data.datasource.remote

import com.fadali.flod_restaurant.feature.profile.data.model.ActivitiesLookupResponse
import com.fadali.flod_restaurant.feature.profile.data.model.AllergiesLookupResponse
import com.fadali.flod_restaurant.feature.profile.data.model.GoalsLookupResponse
import com.fadali.flod_restaurant.feature.profile.domain.model.CompleteUserDataRequest
import com.fadali.flod_restaurant.feature.profile.domain.model.UpdateUserAllergiesRequest
import com.fadali.flod_restaurant.feature.profile.domain.model.UserProfile

interface ProfileRemoteDataSource {
    suspend fun getActivitiesLookup(): ActivitiesLookupResponse
    suspend fun getGoalsLookup(): GoalsLookupResponse
    suspend fun getAllergiesLookup(query: String? = null): AllergiesLookupResponse
    suspend fun completeUserData(request: CompleteUserDataRequest): Boolean
    suspend fun updateUserAllergies(request: UpdateUserAllergiesRequest): Boolean
    suspend fun getUserProfile(): UserProfile
}
