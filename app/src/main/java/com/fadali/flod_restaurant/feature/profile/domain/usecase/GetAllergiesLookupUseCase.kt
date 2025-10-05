package com.fadali.flod_restaurant.feature.profile.domain.usecase

import com.fadali.flod_restaurant.feature.profile.domain.ProfileRepo

class GetAllergiesLookupUseCase(
    private val repo: ProfileRepo
) {
    suspend operator fun invoke(query: String? = null) = repo.getAllergiesLookup(query)
}
