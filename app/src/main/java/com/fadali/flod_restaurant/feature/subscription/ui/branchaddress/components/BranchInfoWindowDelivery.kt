package com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun BranchInfoWindowDelivery(
    modifier: Modifier = Modifier,
    isAddingNewAddress: Boolean,
    addNewAddressState: AddNewAddressState,
    selectDeliveryAddressState: SelectDeliveryAddressState,
    onRequestCurrentUserLocation: () -> Unit,
) {
    if (isAddingNewAddress) {
        AddNewAddressInfoWindow(
            modifier = modifier.fillMaxWidth(),
            selectedAddress = addNewAddressState.selectedAddress,
            selectedAddressType = addNewAddressState.selectedAddressType,
            onAddressTypeSelected = addNewAddressState.onAddressTypeSelected,
            onEditSelectedAddressClicked = addNewAddressState.onEditSelectedAddressClicked,
            streetName = addNewAddressState.streetName,
            buildingName = addNewAddressState.buildingName,
            flatNumber = addNewAddressState.flatNumber,
            nearestSign = addNewAddressState.nearestSign,
            onStreetNameChanged = addNewAddressState.onStreetNameChanged,
            onBuildingNameChanged = addNewAddressState.onBuildingNameChanged,
            onFlatNumberChanged = addNewAddressState.onFlatNumberChanged,
            onNearestSignChanged = addNewAddressState.onNearestSignChanged,
            onConfirmClicked = addNewAddressState.onConfirmClicked
        )
    } else {
        SelectDeliveryAddressInfoWindow(
            modifier = modifier.fillMaxWidth(),
            homeAddress = selectDeliveryAddressState.homeAddress,
            workAddress = selectDeliveryAddressState.workAddress,
            isHomeSelected = selectDeliveryAddressState.isHomeSelected,
            onAddressSelected = selectDeliveryAddressState.onAddressSelected,
            onAddNewAddressClicked = selectDeliveryAddressState.onAddNewAddressClicked,
            onConfirmClicked = selectDeliveryAddressState.onConfirmClicked,
            onRequestCurrentUserLocation = onRequestCurrentUserLocation
        )
    }
}

@Preview
@Composable
private fun BranchInfoWindowDeliveryPreview1() {
    FLODRestaurantTheme {
        BranchInfoWindowDelivery(
            isAddingNewAddress = true,
            addNewAddressState = AddNewAddressState(
                selectedAddress = "123 Main St, City, Country",
                selectedAddressType = com.fadali.flod_restaurant.core.network.appconfig.AddressType.HOME,
                onAddressTypeSelected = {},
                onEditSelectedAddressClicked = {},
                streetName = "",
                buildingName = "",
                flatNumber = "",
                nearestSign = "",
                onStreetNameChanged = {},
                onBuildingNameChanged = {},
                onFlatNumberChanged = {},
                onNearestSignChanged = {},
                onConfirmClicked = {}
            ),
            selectDeliveryAddressState = SelectDeliveryAddressState(
                homeAddress = com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress.empty.copy(
                    displayName = "123 Main St, City, Country"
                ),
                workAddress = com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress.empty.copy(
                    displayName = "456 Work Rd, City, Country"
                ),
                isHomeSelected = true,
                onAddressSelected = {},
                onAddNewAddressClicked = {},
                onConfirmClicked = {}
            ),
            onRequestCurrentUserLocation = {}
        )
    }
}

@Preview
@Composable
private fun BranchInfoWindowDeliveryPreview2() {
    FLODRestaurantTheme {
        BranchInfoWindowDelivery(
            isAddingNewAddress = false,
            addNewAddressState = AddNewAddressState(
                selectedAddress = "123 Main St, City, Country",
                selectedAddressType = com.fadali.flod_restaurant.core.network.appconfig.AddressType.HOME,
                onAddressTypeSelected = {},
                onEditSelectedAddressClicked = {},
                streetName = "",
                buildingName = "",
                flatNumber = "",
                nearestSign = "",
                onStreetNameChanged = {},
                onBuildingNameChanged = {},
                onFlatNumberChanged = {},
                onNearestSignChanged = {},
                onConfirmClicked = {}
            ),
            selectDeliveryAddressState = SelectDeliveryAddressState(
                homeAddress = com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress.empty.copy(
                    displayName = "123 Main St, City, Country"
                ),
                workAddress = com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress.empty.copy(
                    displayName = "456 Work Rd, City, Country"
                ),
                isHomeSelected = true,
                onAddressSelected = {},
                onAddNewAddressClicked = {},
                onConfirmClicked = {}
            ),
            onRequestCurrentUserLocation = {}
        )
    }
}
