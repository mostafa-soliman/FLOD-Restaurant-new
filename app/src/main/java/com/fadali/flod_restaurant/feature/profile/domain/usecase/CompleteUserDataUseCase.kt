package com.fadali.flod_restaurant.feature.profile.domain.usecase

import com.fadali.flod_restaurant.feature.profile.domain.ProfileRepo
import com.fadali.flod_restaurant.feature.profile.domain.model.CompleteUserDataRequest

class CompleteUserDataUseCase(
    private val repo: ProfileRepo
) {
    suspend operator fun invoke(request: CompleteUserDataRequest) = repo.completeUserData(request)
}
