package com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components

import com.fadali.flod_restaurant.core.network.appconfig.AddressType

data class AddNewAddressState(
    val selectedAddress: String,
    val selectedAddressType: AddressType,
    val onAddressTypeSelected: (addressType: AddressType) -> Unit,
    val onEditSelectedAddressClicked: () -> Unit,
    val streetName: String,
    val buildingName: String,
    val flatNumber: String,
    val nearestSign: String,
    val onStreetNameChanged: (String) -> Unit,
    val onBuildingNameChanged: (String) -> Unit,
    val onFlatNumberChanged: (String) -> Unit,
    val onNearestSignChanged: (String) -> Unit,
    val onConfirmClicked: () -> Unit,
)
