package com.fadali.flod_restaurant.feature.subscription.domain.usecase

import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo
import com.fadali.flod_restaurant.feature.subscription.domain.model.payment.CreateGeideaPaymentRequest

data class CreateGeideaPaymentUseCase(
    private val repo: SubscriptionRepo
) {
    suspend operator fun invoke(request: CreateGeideaPaymentRequest) = repo.createGeideaPayment(request)
}
