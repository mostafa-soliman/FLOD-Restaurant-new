package com.fadali.flod_restaurant.feature.subscription.data

import com.fadali.flod_restaurant.feature.subscription.data.datasource.remote.SubscriptionRemoteDataSource
import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CreateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.UpdateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.GetPackagesRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails

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
}
