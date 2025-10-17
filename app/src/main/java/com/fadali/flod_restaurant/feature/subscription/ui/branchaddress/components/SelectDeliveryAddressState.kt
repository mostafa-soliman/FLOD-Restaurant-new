package com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components

import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress

data class SelectDeliveryAddressState(
    val homeAddress: CustomerAddress?,
    val workAddress: CustomerAddress?,
    val isHomeSelected: Boolean,
    val onAddressSelected: (CustomerAddress) -> Unit,
    val onAddNewAddressClicked: () -> Unit,
    val onConfirmClicked: () -> Unit
)
