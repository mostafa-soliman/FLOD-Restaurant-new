package com.fadali.flod_restaurant.feature.subscription.domain.usecase

import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo
import com.fadali.flod_restaurant.feature.subscription.domain.model.payment.CreateSubscriptionRequest

class CreateSubscriptionUseCase(
    private val repo: SubscriptionRepo
) {
    suspend operator fun invoke(request: CreateSubscriptionRequest) = repo.createSubscription(request)
}
