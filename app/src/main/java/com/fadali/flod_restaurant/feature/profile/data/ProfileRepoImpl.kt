package com.fadali.flod_restaurant.feature.profile.data

import com.fadali.flod_restaurant.feature.profile.data.mapper.toDomainModel
import com.fadali.flod_restaurant.feature.profile.data.remote.ProfileRemoteDataSource
import com.fadali.flod_restaurant.feature.profile.domain.ProfileRepo
import com.fadali.flod_restaurant.feature.profile.domain.model.ActivitiesLookupModel
import com.fadali.flod_restaurant.feature.profile.domain.model.AllergiesLookupModel
import com.fadali.flod_restaurant.feature.profile.domain.model.CompleteUserDataRequest
import com.fadali.flod_restaurant.feature.profile.domain.model.GoalsLookupModel
import com.fadali.flod_restaurant.feature.profile.domain.model.UpdateUserAllergiesRequest

class ProfileRepoImpl(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepo {
    override suspend fun getActivitiesLookup(): ActivitiesLookupModel {
        return remoteDataSource.getActivitiesLookup().toDomainModel()
    }

    override suspend fun getGoalsLookup(): GoalsLookupModel {
        return remoteDataSource.getGoalsLookup().toDomainModel()
    }

    override suspend fun getAllergiesLookup(query: String?): AllergiesLookupModel {
        return remoteDataSource.getAllergiesLookup(query).toDomainModel()
    }

    override suspend fun completeUserData(request: CompleteUserDataRequest): Boolean {
        return remoteDataSource.completeUserData(request = request)
    }

    override suspend fun updateUserAllergies(request: UpdateUserAllergiesRequest): Boolean {
        return remoteDataSource.updateUserAllergies(request = request)
    }
}
