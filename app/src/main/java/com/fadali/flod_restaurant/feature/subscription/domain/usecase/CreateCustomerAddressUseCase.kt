package com.fadali.flod_restaurant.feature.subscription.domain.usecase

import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CreateCustomerAddressRequest

class CreateCustomerAddressUseCase(
    private val repo: SubscriptionRepo
) {
    suspend operator fun invoke(request: CreateCustomerAddressRequest) = 
        repo.createCustomerAddress(request)
}
