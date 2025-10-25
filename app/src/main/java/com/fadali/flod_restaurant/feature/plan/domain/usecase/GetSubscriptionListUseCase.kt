package com.fadali.flod_restaurant.feature.plan.domain.usecase

import com.fadali.flod_restaurant.feature.plan.domain.PlanRepo
import com.fadali.flod_restaurant.feature.plan.domain.model.GetSubscriptionListRequest
import com.fadali.flod_restaurant.feature.plan.domain.model.SubscriptionListResponse

class GetSubscriptionListUseCase(
    private val planRepo: PlanRepo
) {
    suspend operator fun invoke(request: GetSubscriptionListRequest): SubscriptionListResponse {
        return planRepo.getSubscriptionList(request)
    }
}

