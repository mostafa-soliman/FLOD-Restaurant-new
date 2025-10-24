package com.fadali.flod_restaurant.feature.subscription.domain.usecase

import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo

class GetTermsAndConditionsUseCase(
    private val subscriptionRepo: SubscriptionRepo
) {
    suspend operator fun invoke(): String {
        return subscriptionRepo.fetchTermsAndConditionsHtml()
    }
}
