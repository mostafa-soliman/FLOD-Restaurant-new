package com.fadali.flod_restaurant.feature.subscription.domain.usecase

import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo

class DeleteCustomerAddressUseCase(
    private val repo: SubscriptionRepo
) {
    suspend operator fun invoke(addressId: String) = 
        repo.deleteCustomerAddresses(addressId)
}
