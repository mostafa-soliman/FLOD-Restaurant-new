package com.fadali.flod_restaurant.feature.profile.data

import com.fadali.flod_restaurant.feature.profile.data.mapper.toDomainModel
import com.fadali.flod_restaurant.feature.profile.data.remote.ProfileRemoteDataSource
import com.fadali.flod_restaurant.feature.profile.domain.ProfileRepo
import com.fadali.flod_restaurant.feature.profile.domain.model.ActivitiesLookupResponse
import com.fadali.flod_restaurant.feature.profile.domain.model.CompleteUserDataRequest
import com.fadali.flod_restaurant.feature.profile.domain.model.GoalsLookupResponse

class ProfileRepoImpl(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepo {
    override suspend fun getActivitiesLookup(): ActivitiesLookupResponse {
        return remoteDataSource.getActivitiesLookup().toDomainModel()
    }

    override suspend fun getGoalsLookup(): GoalsLookupResponse {
        return remoteDataSource.getGoalsLookup().toDomainModel()
    }

    override suspend fun completeUserData(request: CompleteUserDataRequest): Boolean {
        return remoteDataSource.completeUserData(request = request)
    }
}
