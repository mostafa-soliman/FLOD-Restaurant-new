package com.fadali.flod_restaurant.feature.subscription.domain.usecase

import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.GetPackageByIdResponse

class GetPackageByIdUseCase(
    private val subscriptionRepo: SubscriptionRepo
) {
    suspend operator fun invoke(id: String): GetPackageByIdResponse {
        return subscriptionRepo.getPackageById(id)
    }
}
