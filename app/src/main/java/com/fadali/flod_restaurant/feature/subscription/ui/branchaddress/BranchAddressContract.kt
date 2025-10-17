package com.fadali.flod_restaurant.feature.subscription.ui.branchaddress

import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.network.appconfig.AddressType
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.core.util.MapsUtils
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui.utils.BranchAddressResult
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class BranchAddressState(
    val isLoading: Boolean = false,
    val isLocationPermissionGranted: Boolean = false,
    val userCurrentLocation: LatLng? = null,
    val args: SubscriptionRoutes.BranchAddress? = null,
    val selectedBranch: Branch = Branch.empty,
    val defaultMapCenterLocation: LatLng = LatLng(24.7136, 46.6753), // Riyadh coordinates
    val userManuallySelectedLocation: LatLng? = null,
    val distanceBetweenUserAndBranchInKm: Double = 0.0,
    val distanceBetweenSelectedLocationAndBranchInKm: Double = 0.0,
    val mapCameraPositionState: CameraPositionState = CameraPositionState(),
    val selectedAddressType: AddressType = AddressType.HOME,
    val isAddingNewAddress: Boolean = false,
    val selectedLocationAddress: MapsUtils.DetailedAddress? = null,
    val selectedLocationAddressText: DeferredString? = null,
    val streetName: String = "",
    val buildingName: String = "",
    val flatNumber: String = "",
    val nearestSign: String = "",
    val homeAddress: CustomerAddress? = null,
    val workAddress: CustomerAddress? = null,
    val isHomeSelected: Boolean = true,
) : UiState

sealed interface BranchAddressEvent : UiEvent {
    data class LoadArgs(val args: SubscriptionRoutes.BranchAddress) : BranchAddressEvent
    data object OnRequestLocationPermission : BranchAddressEvent
    data class OnLocationPermissionResult(val isGranted: Boolean) : BranchAddressEvent
    data class OnBranchSelected(val branch: Branch) : BranchAddressEvent
    data object OnRequestCurrentUserLocation : BranchAddressEvent
    data class UpdateCurrentUserLocation(val location: LatLng?) : BranchAddressEvent
    data class OnUserManuallySelectsLocation(val location: LatLng) : BranchAddressEvent
    data object OnLocationPermissionDenied : BranchAddressEvent
    data class OnSelectedLocationAddressChanged(val address: MapsUtils.DetailedAddress?) : BranchAddressEvent
    data object OnAddNewAddressClicked : BranchAddressEvent
    data class OnAddressTypeSelected(val addressType: AddressType) : BranchAddressEvent
    data object OnEditSelectedLocation : BranchAddressEvent
    data class OnStreetNameChanged(val streetName: String) : BranchAddressEvent
    data class OnBuildingNameChanged(val buildingName: String) : BranchAddressEvent
    data class OnFlatNumberChanged(val flatNumber: String) : BranchAddressEvent
    data class OnNearestSignChanged(val nearestSign: String) : BranchAddressEvent
    data object OnConfirmAddingNewAddress: BranchAddressEvent
    data class OnSelectDeliveryAddress(val address: CustomerAddress) : BranchAddressEvent
    data object OnConfirmBranchSelection : BranchAddressEvent
    data object OnConfirmDeliveryAddressSelection : BranchAddressEvent
}

sealed interface BranchAddressEffect : UiEffect {
    data object RequestLocationPermission : BranchAddressEffect
    data object OpenAppLocationSettings : BranchAddressEffect
    data object GetCurrentUserLocation : BranchAddressEffect
    data class NavigateBackWithResult(val result: BranchAddressResult) : BranchAddressEffect
    data class GetSelectedAddressFromLocation(val location: LatLng) : BranchAddressEffect
    data class ShowError(val dialogState: ErrorDialogState?) : BranchAddressEffect
}
