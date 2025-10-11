package com.fadali.flod_restaurant.feature.subscription.data.datasource.remote

import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.network.ResponseModel
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CreateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.UpdateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.GetPackagesRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails
import com.smyvo.core.network.NetworkHandler
import com.smyvo.core.utils.filterNotNull

class SubscriptionRemoteDataSourceImpl(
    private val networkHandler: NetworkHandler
) : SubscriptionRemoteDataSource {
    override suspend fun getProductCategories(): List<ProductCategory> {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_GET_PRODUCT_CATEGORIES}"
        val response = networkHandler.get<ResponseModel<List<ProductCategory>>>(
            url = url,
        )
        return response.data!!
    }

    override suspend fun getBranches(): List<Branch> {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_GET_BRANCHES}"
        val response = networkHandler.get<ResponseModel<List<Branch>>>(
            url = url,
        )
        return response.data!!
    }

    override suspend fun getCustomerAddresses(): List<CustomerAddress> {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_GET_CUSTOMER_ADDRESSES}"
        val response = networkHandler.get<ResponseModel<List<CustomerAddress>>>(
            url = url,
        )
        return response.data!!
    }

    override suspend fun createCustomerAddress(request: CreateCustomerAddressRequest): Boolean {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_CREATE_CUSTOMER_ADDRESS}"
        val response = networkHandler.post<ResponseModel<Boolean>>(
            url = url,
            data = request
        )
        return response.data!!
    }

    override suspend fun updateCustomerAddresses(request: UpdateCustomerAddressRequest): Boolean {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_UPDATE_CUSTOMER_ADDRESS}"
        val response = networkHandler.put<ResponseModel<Boolean>>(
            url = url,
            data = request
        )
        return response.data!!
    }

    override suspend fun deleteCustomerAddresses(addressId: String): Boolean {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_DELETE_CUSTOMER_ADDRESS}"
        val response = networkHandler.delete<ResponseModel<Boolean>>(
            url = url,
            pathParams = listOf(addressId)
        )
        return response.data!!
    }

    override suspend fun getPackages(request: GetPackagesRequest): List<PackageDetails> {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_GET_PACKAGES}"
        val queryParams = mapOf(
            KEY_QUERY_PARAM_PACKAGE_TYPE to request.packageType.toString(),
            KEY_QUERY_PARAM_PRODUCT_CATEGORY_ID to request.productCategoryId,
            KEY_QUERY_PARAM_CUSTOMER_ADDRESS_ID to request.customerAddressId,
            KEY_QUERY_PARAM_BRANCH_ID to request.branchId
        ).filterNotNull<String, String>()

        val response = networkHandler.get<ResponseModel<List<PackageDetails>>>(
            url = url,
            queryParams = queryParams
        )
        return response.data!!
    }

    private companion object {
        const val KEY_QUERY_PARAM_PACKAGE_TYPE = "PackageType"
        const val KEY_QUERY_PARAM_PRODUCT_CATEGORY_ID = "MainProductCategoryId"
        const val KEY_QUERY_PARAM_CUSTOMER_ADDRESS_ID = "CustomerAddressId"
        const val KEY_QUERY_PARAM_BRANCH_ID = "BranchId"
    }
}
