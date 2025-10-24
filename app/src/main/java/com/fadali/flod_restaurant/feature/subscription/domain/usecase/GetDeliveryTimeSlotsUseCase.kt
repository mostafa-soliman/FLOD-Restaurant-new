package com.fadali.flod_restaurant.feature.subscription.domain.usecase

import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo

class GetDeliveryTimeSlotsUseCase(
    private val repo: SubscriptionRepo
) {
    suspend operator fun invoke(
        addressId: String,
        packageId: String
    ) = repo.getDeliveryTimeSlots(
        addressId = addressId,
        packageId = packageId
    )
}
