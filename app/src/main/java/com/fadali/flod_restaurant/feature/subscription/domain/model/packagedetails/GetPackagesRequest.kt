package com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GetPackagesRequest(
    val productCategoryId: String,
    val packageType: Int,
    val customerAddressId: String? = null,
    val branchId: String? = null,
)
