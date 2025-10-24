package com.fadali.flod_restaurant.feature.subscription.data.datasource.remote

import com.fadali.flod_restaurant.feature.subscription.domain.model.addons.AddonsResponse
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

interface SubscriptionRemoteDataSource {
    suspend fun getProductCategories(): List<ProductCategory>
    suspend fun getBranches(): List<Branch>
    suspend fun getCustomerAddresses(): List<CustomerAddress>
    suspend fun createCustomerAddress(request: CreateCustomerAddressRequest): Boolean
    suspend fun updateCustomerAddresses(request: UpdateCustomerAddressRequest): Boolean
    suspend fun deleteCustomerAddresses(addressId: String): Boolean
    suspend fun getPackages(request: GetPackagesRequest): List<PackageDetails>
    suspend fun getPackageById(id: String): GetPackageByIdResponse
    suspend fun getDeliveryTimeSlots(addressId: String, packageId: String): List<DeliveryTimeSlot>
    suspend fun getAddons(): AddonsResponse
    suspend fun validatePromoCode(promoCode: String): PromoCode?
    suspend fun createSubscription(request: CreateSubscriptionRequest): String
    suspend fun createGeideaPayment(request: CreateGeideaPaymentRequest): CreateGeideaPaymentResponse
    suspend fun getTermsAndConditionsHtml(): String
}
