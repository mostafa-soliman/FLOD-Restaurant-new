package com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui.utils

import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import kotlinx.serialization.Serializable

@Serializable
data class BranchAddressResult(
    val selectedBranch: Branch? = null,
    val deliveryAddress: CustomerAddress? = null,
)
