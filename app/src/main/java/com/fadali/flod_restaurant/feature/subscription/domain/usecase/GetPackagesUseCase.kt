package com.fadali.flod_restaurant.feature.subscription.domain.usecase

import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.GetPackagesRequest

class GetPackagesUseCase(
    private val repo: SubscriptionRepo
) {
    suspend operator fun invoke(request: GetPackagesRequest) = repo.getPackages(request)
}
