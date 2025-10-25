package com.fadali.flod_restaurant.feature.plan.domain.usecase

import com.fadali.flod_restaurant.feature.plan.domain.PlanRepo
import com.fadali.flod_restaurant.feature.plan.domain.model.PackageDetailsResponse

class GetPackageDetailsUseCase(
    private val planRepo: PlanRepo
) {
    suspend operator fun invoke(subscriptionId: String): PackageDetailsResponse {
        return planRepo.getSubscriptionDetails(subscriptionId)
    }
}
