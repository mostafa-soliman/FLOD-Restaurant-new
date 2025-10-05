package com.fadali.flod_restaurant.feature.profile.domain.usecase

import com.fadali.flod_restaurant.feature.profile.domain.ProfileRepo
import com.fadali.flod_restaurant.feature.profile.domain.model.UpdateUserAllergiesRequest

class UpdateUserAllergiesUseCase(
    private val repo: ProfileRepo
) {
    suspend operator fun invoke(request: UpdateUserAllergiesRequest) = repo.updateUserAllergies(request)
}
