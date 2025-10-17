package com.fadali.flod_restaurant.feature.subscription.ui.branchaddress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.savedstate.serialization.encodeToSavedState
import com.fadali.flod_restaurant.app.navigation.navigateBack
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.core.util.MapsUtils.getBestAvailableUserLocation
import com.fadali.flod_restaurant.core.util.MapsUtils.getDetailedAddressFromLocation
import com.fadali.flod_restaurant.core.util.PermissionUtils
import com.fadali.flod_restaurant.core.util.PermissionUtils.getPermissionsActivityResultLauncher
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.LoadArgs
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnAddNewAddressClicked
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnAddressTypeSelected
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnBranchSelected
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnBuildingNameChanged
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnConfirmAddingNewAddress
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnConfirmBranchSelection
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnEditSelectedLocation
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnFlatNumberChanged
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnLocationPermissionDenied
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnLocationPermissionResult
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnNearestSignChanged
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnRequestCurrentUserLocation
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnRequestLocationPermission
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnSelectDeliveryAddress
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnSelectedLocationAddressChanged
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnStreetNameChanged
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.OnUserManuallySelectsLocation
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressEvent.UpdateCurrentUserLocation
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components.AddNewAddressState
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components.BranchInfoWindowDelivery
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components.BranchInfoWindowPickup
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components.MapView
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components.SelectDeliveryAddressState
import com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui.utils.SelectPlanConstants.KEY_RESULT_BRANCH_ADDRESS
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.google.android.gms.maps.model.LatLng
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BranchAddressScreen(
    navController: NavController,
    viewModel: BranchAddressViewModel = koinViewModel(),
    args: SubscriptionRoutes.BranchAddress
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val locationRequestLauncher = getPermissionsActivityResultLauncher(
        onPermissionsGranted = {
            viewModel.sendEvent(OnLocationPermissionResult(isGranted = true))
        },
        onPermissionsDenied = {
            viewModel.sendEvent(OnLocationPermissionDenied)
        }
    )

    LaunchedEffect(Unit) {
        viewModel.sendEvent(OnRequestLocationPermission)
        viewModel.sendEvent(LoadArgs(args))
    }
    HandleState(viewModel.state) { state ->
        if (state.args != null) {
            BranchAddressScreenContent(
                state = state,
                sendEvent = viewModel::sendEvent
            )
        }
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            BranchAddressEffect.RequestLocationPermission -> {
                locationRequestLauncher.launch(PermissionUtils.LOCATION_PERMISSIONS)
            }
            BranchAddressEffect.OpenAppLocationSettings -> {
                PermissionUtils.openAppSettings(context)
            }
            is BranchAddressEffect.ShowError -> {
                showErrorDialog = true
                effect.dialogState?.let { errorDialogState = it }
            }
            BranchAddressEffect.GetCurrentUserLocation -> {
                coroutineScope.launch {
                    val currentLocation = getBestAvailableUserLocation(context)
                    viewModel.sendEvent(UpdateCurrentUserLocation(currentLocation))
                }
            }
            is BranchAddressEffect.GetSelectedAddressFromLocation -> {
                getDetailedAddressFromLocation(
                    context = context,
                    location = effect.location,
                    onResult = { address ->
                        viewModel.sendEvent(OnSelectedLocationAddressChanged(address))
                    }
                )
            }
            is BranchAddressEffect.NavigateBackWithResult -> {
                val result = encodeToSavedState(effect.result)
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    key = KEY_RESULT_BRANCH_ADDRESS,
                    value = result
                )
                navController.navigateBack()
            }
        }
    }
    AppErrorDialog(
        isVisible = showErrorDialog,
        title = errorDialogState.title.resolve(),
        message = errorDialogState.message.resolve(),
        primaryButtonText = errorDialogState.primaryButtonText.resolve(),
        secondaryButtonText = errorDialogState.secondaryButtonText?.resolve(),
        isDismissible = errorDialogState.isDismissible,
        onPrimaryBtnClicked = errorDialogState.onPrimaryBtnClicked,
        onDismiss = {
            showErrorDialog = false
            errorDialogState.onDismiss()
        }
    )
}

@Composable
private fun BranchAddressScreenContent(
    state: BranchAddressState,
    sendEvent: (BranchAddressEvent) -> Unit
) {
    var isAddingNewAddress by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        AppLoadingDialog(isVisible = state.isLoading)

        MapView(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = state.mapCameraPositionState,
            userLocation = state.userCurrentLocation,
            userManuallySelectedLocation =
                if (state.args?.packageType == PackageType.DELIVERY) state.userManuallySelectedLocation else null,
            restaurantBranches = if (isAddingNewAddress) emptyList() else state.args?.branches
                ?: emptyList(),
            selectedBranch = state.selectedBranch,
            onMapClick = { sendEvent(OnUserManuallySelectsLocation(it)) },
            isLocationPermissionGranted = state.isLocationPermissionGranted,
            onBranchClick = { sendEvent(OnBranchSelected(it)) }
        )
        if (state.args?.packageType == PackageType.PICKUP) {
            BranchInfoWindowPickup(
                modifier = Modifier.align(Alignment.BottomCenter),
                branchName = state.selectedBranch.displayName,
                branchAddress = state.selectedBranch.displayName,
                distance = getDisplayableDistance(state.distanceBetweenUserAndBranchInKm),
                branchCoordinates = LatLng(
                    state.selectedBranch.addressLat,
                    state.selectedBranch.addressLng
                ),
                onConfirmSelection = { sendEvent(OnConfirmBranchSelection) },
                isLocationPermissionEnabled = state.isLocationPermissionGranted,
                onRequestLocationPermission = { sendEvent(OnRequestLocationPermission) },
                onRequestCurrentUserLocation = { sendEvent(OnRequestCurrentUserLocation) }
            )
        } else {
            BranchInfoWindowDelivery(
                modifier = Modifier.align(Alignment.BottomCenter),
                isAddingNewAddress = state.isAddingNewAddress,
                addNewAddressState = AddNewAddressState(
                    selectedAddress = state.selectedLocationAddressText?.resolve() ?: "",
                    selectedAddressType = state.selectedAddressType,
                    onAddressTypeSelected = { sendEvent(OnAddressTypeSelected(it)) },
                    onEditSelectedAddressClicked = { sendEvent(OnEditSelectedLocation) },
                    streetName = state.streetName,
                    buildingName = state.buildingName,
                    flatNumber = state.flatNumber,
                    nearestSign = state.nearestSign,
                    onStreetNameChanged = { sendEvent(OnStreetNameChanged(it)) },
                    onBuildingNameChanged = { sendEvent(OnBuildingNameChanged(it)) },
                    onFlatNumberChanged = { sendEvent(OnFlatNumberChanged(it)) },
                    onNearestSignChanged = { sendEvent(OnNearestSignChanged(it)) },
                    onConfirmClicked = { sendEvent(OnConfirmAddingNewAddress) }
                ),
                selectDeliveryAddressState = SelectDeliveryAddressState(
                    homeAddress = state.homeAddress,
                    workAddress = state.workAddress,
                    isHomeSelected = state.isHomeSelected,
                    onAddressSelected = { sendEvent(OnSelectDeliveryAddress(it)) },
                    onAddNewAddressClicked = { sendEvent(OnAddNewAddressClicked) },
                    onConfirmClicked = { sendEvent(BranchAddressEvent.OnConfirmDeliveryAddressSelection) }
                ),
                onRequestCurrentUserLocation = { sendEvent(OnRequestCurrentUserLocation) }
            )
        }
    }
}

private fun getDisplayableDistance(distanceInKm: Double): String {
    return if (distanceInKm == 0.0) {
        "-- Km"
    } else if (distanceInKm < 1) {
        val distanceInMeters = (distanceInKm * 1000).toInt()
        "$distanceInMeters M"
    } else {
        String.format("%.1f Km", distanceInKm)
    }
}

@AppPreviews
@Composable
private fun BranchAddressScreenContentPreview() {
    FLODRestaurantTheme {
        BranchAddressScreenContent(
            state = BranchAddressState(),
            sendEvent = {}
        )
    }
}
