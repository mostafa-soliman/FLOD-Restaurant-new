package com.fadali.flod_restaurant.feature.subscription.data

import com.fadali.flod_restaurant.feature.subscription.data.datasource.remote.SubscriptionRemoteDataSource
import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo
import com.fadali.flod_restaurant.feature.subscription.domain.model.addons.Addon
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CreateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.UpdateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.GetPackageByIdResponse
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.GetPackagesRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails
import com.fadali.flod_restaurant.feature.subscription.domain.model.payment.CreateGeideaPaymentRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.payment.CreateGeideaPaymentResponse
import com.fadali.flod_restaurant.feature.subscription.domain.model.payment.CreateSubscriptionRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.promocode.PromoCode
import com.fadali.flod_restaurant.feature.subscription.domain.model.timeslots.DeliveryTimeSlot

class SubscriptionRepoImpl(
    private val remoteDataSource: SubscriptionRemoteDataSource
) : SubscriptionRepo {
    override suspend fun getProductCategories(): List<ProductCategory> {
        return remoteDataSource.getProductCategories()
    }

    override suspend fun getBranches(): List<Branch> {
        return remoteDataSource.getBranches()
    }

    override suspend fun getCustomerAddresses(): List<CustomerAddress> {
        return remoteDataSource.getCustomerAddresses()
    }

    override suspend fun createCustomerAddress(request: CreateCustomerAddressRequest): Boolean {
        return remoteDataSource.createCustomerAddress(request)
    }

    override suspend fun updateCustomerAddresses(request: UpdateCustomerAddressRequest): Boolean {
        return remoteDataSource.updateCustomerAddresses(request)
    }

    override suspend fun deleteCustomerAddresses(addressId: String): Boolean {
        return remoteDataSource.deleteCustomerAddresses(addressId)
    }

    override suspend fun getPackages(request: GetPackagesRequest): List<PackageDetails> {
        return remoteDataSource.getPackages(request)
    }

    override suspend fun getPackageById(id: String): GetPackageByIdResponse {
        return remoteDataSource.getPackageById(id)
    }

    override suspend fun getDeliveryTimeSlots(
        addressId: String,
        packageId: String
    ): List<DeliveryTimeSlot> {
        return remoteDataSource.getDeliveryTimeSlots(
            addressId = addressId,
            packageId = packageId
        )
    }

    override suspend fun getAddons(): List<Addon> {
        return remoteDataSource.getAddons().items
    }

    override suspend fun validatePromoCode(promoCode: String): PromoCode? {
        return remoteDataSource.validatePromoCode(promoCode)
    }

    override suspend fun createSubscription(request: CreateSubscriptionRequest): String {
        return remoteDataSource.createSubscription(request)
    }

    override suspend fun createGeideaPayment(request: CreateGeideaPaymentRequest): CreateGeideaPaymentResponse {
        return remoteDataSource.createGeideaPayment(request)
    }

    override suspend fun fetchTermsAndConditionsHtml(): String {
        return remoteDataSource.getTermsAndConditionsHtml()
    }

}
