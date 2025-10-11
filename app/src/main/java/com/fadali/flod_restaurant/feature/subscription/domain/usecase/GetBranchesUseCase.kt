package com.fadali.flod_restaurant.feature.subscription.domain.usecase

import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo

class GetBranchesUseCase(
    private val repo: SubscriptionRepo
) {
    suspend operator fun invoke() = repo.getBranches()
}
