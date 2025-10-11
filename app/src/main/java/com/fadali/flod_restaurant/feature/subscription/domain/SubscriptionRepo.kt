package com.fadali.flod_restaurant.feature.subscription.domain

import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CreateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.UpdateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.GetPackagesRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails

interface SubscriptionRepo {
    suspend fun getProductCategories(): List<ProductCategory>
    suspend fun getBranches(): List<Branch>
    suspend fun getCustomerAddresses(): List<CustomerAddress>
    suspend fun createCustomerAddress(request: CreateCustomerAddressRequest): Boolean
    suspend fun updateCustomerAddresses(request: UpdateCustomerAddressRequest): Boolean
    suspend fun deleteCustomerAddresses(addressId: String): Boolean
    suspend fun getPackages(request: GetPackagesRequest): List<PackageDetails>
}
