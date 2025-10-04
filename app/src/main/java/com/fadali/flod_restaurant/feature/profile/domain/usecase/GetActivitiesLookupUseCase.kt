package com.fadali.flod_restaurant.feature.profile.domain.usecase

import com.fadali.flod_restaurant.feature.profile.domain.ProfileRepo

class GetActivitiesLookupUseCase(
    private val repo: ProfileRepo
) {
    suspend operator fun invoke() = repo.getActivitiesLookup()
}
