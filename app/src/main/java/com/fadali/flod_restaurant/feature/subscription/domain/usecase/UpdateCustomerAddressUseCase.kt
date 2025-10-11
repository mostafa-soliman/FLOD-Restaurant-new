package com.fadali.flod_restaurant.feature.subscription.domain.usecase

import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.UpdateCustomerAddressRequest

class UpdateCustomerAddressUseCase(
    private val repo: SubscriptionRepo
) {
    suspend operator fun invoke(request: UpdateCustomerAddressRequest) = 
        repo.updateCustomerAddresses(request)
}
