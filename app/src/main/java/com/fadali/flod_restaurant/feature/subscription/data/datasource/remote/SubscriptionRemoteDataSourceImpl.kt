package com.fadali.flod_restaurant.feature.subscription.data.datasource.remote

import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.network.ResponseModel
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

    override suspend fun getPackageById(id: String): GetPackageByIdResponse {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_GET_PACKAGE_BY_ID}"
        val queryParams = mapOf(
            KEY_QUERY_PARAM_GET_PACKAGE_BY_ID to id
        )
        val response = networkHandler.get<ResponseModel<GetPackageByIdResponse>>(
            url = url,
            queryParams = queryParams
        )
        return response.data!!
    }

    override suspend fun getDeliveryTimeSlots(
        addressId: String,
        packageId: String
    ): List<DeliveryTimeSlot> {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_GET_DELIVERY_TIME_SLOTS}"
        val queryParams = mapOf(
            KEY_QUERY_PARAM_ADDRESS_ID to addressId,
            KEY_QUERY_PARAM_PACKAGE_ID to packageId,
        )

        val response = networkHandler.get<ResponseModel<List<DeliveryTimeSlot>>>(
            url = url,
            queryParams = queryParams
        )
        return response.data!!
    }

    override suspend fun getAddons(): AddonsResponse {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_GET_ADDONS}"
        val queryParams = mapOf(
            KEY_QUERY_PARAM_ROW_COUNT to "100",
            KEY_QUERY_PARAM_IS_ACTIVE to "true",
        )

        val response = networkHandler.get<ResponseModel<AddonsResponse>>(
            url = url,
            queryParams = queryParams
        )
        return response.data!!
    }

    override suspend fun validatePromoCode(promoCode: String): PromoCode? {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_VALIDATE_PROMO_CODE}/$promoCode"

        val response = networkHandler.get<ResponseModel<PromoCode>>(
            url = url,
        )
        return response.data
    }

    override suspend fun createSubscription(request: CreateSubscriptionRequest): String {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_CREATE_SUBSCRIPTION}"
        val response = networkHandler.post<ResponseModel<String>>(
            url = url,
            data = request
        )
        return response.data!!
    }

    override suspend fun createGeideaPayment(request: CreateGeideaPaymentRequest): CreateGeideaPaymentResponse {
        val url = "${BuildConfig.BASE_URL}${SubscriptionEndpoints.ENDPOINT_CREATE_GEIDEA_PAYMENT}"
        val response = networkHandler.post<ResponseModel<CreateGeideaPaymentResponse>>(
            url = url,
            data = request
        )
        return response.data!!
    }

    override suspend fun getTermsAndConditionsHtml(): String {
        val endpoint = SubscriptionEndpoints.ENDPOINT_TERMS_AND_CONDITIONS
        if (endpoint.isBlank()) return ""

        val url = "${BuildConfig.BASE_URL}$endpoint"
        return try {
            // TODO:
            // Attempt to fetch raw HTML string. If the network handler wraps responses in ResponseModel,
            // adapt accordingly. Here we attempt to get raw String; if fails, catch and return empty.
            val response = networkHandler.get<String>(url = url)
            // If response is null or blank return empty
            response ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private companion object {
        const val KEY_QUERY_PARAM_PACKAGE_TYPE = "PackageType"
        const val KEY_QUERY_PARAM_PRODUCT_CATEGORY_ID = "MainProductCategoryId"
        const val KEY_QUERY_PARAM_CUSTOMER_ADDRESS_ID = "CustomerAddressId"
        const val KEY_QUERY_PARAM_BRANCH_ID = "BranchId"
        const val KEY_QUERY_PARAM_ADDRESS_ID = "AddressId"
        const val KEY_QUERY_PARAM_PACKAGE_ID = "PackageId"
        const val KEY_QUERY_PARAM_GET_PACKAGE_BY_ID = "id"
        const val KEY_QUERY_PARAM_ROW_COUNT = "RowCount"
        const val KEY_QUERY_PARAM_IS_ACTIVE = "IsActive"
    }
}
