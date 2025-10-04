package com.fadali.flod_restaurant.feature.profile.domain.usecase

import com.fadali.flod_restaurant.feature.profile.domain.ProfileRepo

class GetGoalsLookupUseCase(
    private val repo: ProfileRepo
) {
    suspend operator fun invoke() = repo.getGoalsLookup()
}
