package com.fadali.flod_restaurant.feature.profile.domain.usecase

import com.fadali.flod_restaurant.feature.profile.domain.ProfileRepo

class GetUserProfileUseCase(
    private val repo: ProfileRepo
) {
    suspend operator fun invoke() = repo.getUserProfile()
}
