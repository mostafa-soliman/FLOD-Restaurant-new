package com.fadali.flod_restaurant.feature.subscription.ui.branchaddress

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.network.appconfig.AddressType
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.core.util.MapsUtils.calculateDistanceInKilometers
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.CreateCustomerAddressUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetCustomerAddressesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.UpdateCustomerAddressUseCase
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.mapper.isValidForCreatingOrUpdatingCustomerAddress
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.mapper.toCreateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.mapper.toCustomerAddress
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.mapper.toUpdateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui.utils.BranchAddressResult
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class BranchAddressViewModel(
    private val createCustomerAddressUseCase: CreateCustomerAddressUseCase,
    private val updateCustomerAddressUseCase: UpdateCustomerAddressUseCase,
    private val getCustomerAddressesUseCase: GetCustomerAddressesUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<BranchAddressState, BranchAddressEvent, BranchAddressEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {
    override fun initialState(): BranchAddressState = BranchAddressState()

    override fun handleEvent(event: BranchAddressEvent) {
        when (event) {
            is BranchAddressEvent.LoadArgs -> loadArgs(event)
            is BranchAddressEvent.OnLocationPermissionResult -> onLocationPermissionResult(event)
            BranchAddressEvent.OnRequestLocationPermission -> onRequestLocationPermission()
            BranchAddressEvent.OnConfirmBranchSelection -> onConfirmBranchSelection()
            is BranchAddressEvent.OnBranchSelected -> onBranchSelected(event)
            BranchAddressEvent.OnRequestCurrentUserLocation -> getCurrentUserLocation()
            is BranchAddressEvent.UpdateCurrentUserLocation -> updateCurrentUserLocation(event)
            is BranchAddressEvent.OnUserManuallySelectsLocation -> onUserManuallySelectsLocation(event)
            BranchAddressEvent.OnLocationPermissionDenied -> showPermissionDeniedAlert()
            is BranchAddressEvent.OnSelectedLocationAddressChanged -> onSelectLocationAddressChanged(event)
            BranchAddressEvent.OnEditSelectedLocation -> onEditSelectedLocation()
            BranchAddressEvent.OnConfirmAddingNewAddress -> onConfirmAddingNewAddress()
            is BranchAddressEvent.OnAddressTypeSelected -> setState { copy(selectedAddressType = event.addressType) }
            is BranchAddressEvent.OnStreetNameChanged -> setState { copy(streetName = event.streetName) }
            is BranchAddressEvent.OnBuildingNameChanged -> setState { copy(buildingName = event.buildingName) }
            is BranchAddressEvent.OnNearestSignChanged -> setState { copy(nearestSign = event.nearestSign) }
            is BranchAddressEvent.OnFlatNumberChanged -> setState { copy(flatNumber = event.flatNumber) }
            BranchAddressEvent.OnAddNewAddressClicked -> onAddNewAddressClicked()
            is BranchAddressEvent.OnSelectDeliveryAddress -> onSelectDeliveryAddress(event)
            BranchAddressEvent.OnConfirmDeliveryAddressSelection -> onConfirmDeliveryAddressSelection()
        }
    }

    private fun loadArgs(event: BranchAddressEvent.LoadArgs) {
        val homeAddress = event.args.customerAddresses.find {
            it.addressType == AddressType.HOME.value
        }

        val workAddress = event.args.customerAddresses.find {
            it.addressType == AddressType.WORK.value
        }

        val selectedLocation = if (homeAddress != null) {
            LatLng(homeAddress.latitude, homeAddress.longitude)
        } else if (workAddress != null) {
            LatLng(workAddress.latitude, workAddress.longitude)
        } else {
            currentState.userManuallySelectedLocation
        }
        selectedLocation?.let {
            updateMapCameraPosition(
                location = it,
                zoom = 14f,
            )
        }

        setState {
            copy(
                args = event.args,
                selectedBranch = event.args.branches.first(),
                homeAddress = homeAddress,
                workAddress = workAddress,
                isHomeSelected = homeAddress != null,
                userManuallySelectedLocation = selectedLocation
            )
        }
    }

    private fun onLocationPermissionResult(event: BranchAddressEvent.OnLocationPermissionResult) {
        setState { copy(isLocationPermissionGranted = event.isGranted) }
        if (event.isGranted) {
            getCurrentUserLocation()
        }
    }

    private fun getCurrentUserLocation() {
        setState { copy(isLoading = true) }
        sendEffect(BranchAddressEffect.GetCurrentUserLocation)
    }

    private fun onRequestLocationPermission() {
        val selectedBranchLocation = LatLng(
            currentState.selectedBranch.addressLat,
            currentState.selectedBranch.addressLng
        )
        updateMapCameraPosition(
            location = selectedBranchLocation,
            zoom = 14f,
        )
        sendEffect(BranchAddressEffect.RequestLocationPermission)
    }

    private fun updateMapCameraPosition(
        location: LatLng,
        zoom: Float? = null,
        durationMs: Int = 600,
    ) {
        launchSafe(dispatcher = dispatchers.main) {
            val cameraUpdate: CameraUpdate = if (zoom != null) {
                CameraUpdateFactory.newLatLngZoom(location, zoom)
            } else {
                CameraUpdateFactory.newLatLng(location)
            }
            currentState.mapCameraPositionState.animate(
                update = cameraUpdate,
                durationMs = durationMs
            )
        }
    }

    private fun updateCurrentUserLocation(event: BranchAddressEvent.UpdateCurrentUserLocation) {
        setState { copy(isLoading = false) }
        if (currentState.isLocationPermissionGranted) {
            setState {
                copy(
                    userCurrentLocation = event.location,
                    userManuallySelectedLocation = userManuallySelectedLocation ?: event.location
                )
            }
            val location = event.location ?: currentState.defaultMapCenterLocation
            updateMapCameraPosition(
                location = location,
                zoom = 14f,
                durationMs = 900
            )
            updateDistanceBetweenUserAndBranch()
        } else {
            sendEffect(BranchAddressEffect.RequestLocationPermission)
        }
    }

    private fun showPermissionDeniedAlert() {
        val dialogState = ErrorDialogState.default.copy(
            title = DeferredString.StringResource(R.string.location_permission_required_title),
            message = DeferredString.StringResource(R.string.location_permission_required_message),
            primaryButtonText = DeferredString.StringResource(R.string.enable),
            secondaryButtonText = DeferredString.StringResource(R.string.cancel),
            onPrimaryBtnClicked = { sendEffect(BranchAddressEffect.OpenAppLocationSettings) },
        )
        sendEffect(BranchAddressEffect.ShowError(dialogState))
    }

    private fun onBranchSelected(event: BranchAddressEvent.OnBranchSelected) {
        setState { copy(selectedBranch = event.branch) }
        val location = LatLng(event.branch.addressLat, event.branch.addressLng)
        updateMapCameraPosition(
            location = location,
            zoom = 14f,
        )
        updateDistanceBetweenUserAndBranch()
    }

    private fun onUserManuallySelectsLocation(event: BranchAddressEvent.OnUserManuallySelectsLocation) {
        if (
            currentState.args?.packageType == PackageType.DELIVERY &&
            currentState.isAddingNewAddress.not()
        ) {
            setState { copy(userManuallySelectedLocation = event.location) }
            updateMapCameraPosition(
                location = event.location,
                zoom = 14f,
            )
            updateDistanceBetweenUserAndBranch()
            getSelectedAddressFromLocation(location = currentState.userManuallySelectedLocation!!)
        }
    }

    private fun getSelectedAddressFromLocation(location: LatLng) {
        sendEffect(BranchAddressEffect.GetSelectedAddressFromLocation(location = location))
    }

    private fun updateDistanceBetweenUserAndBranch() {
        val userLocation = currentState.userCurrentLocation
        val userManuallySelectedLocation = currentState.userManuallySelectedLocation
        val branchLocation = LatLng(
            currentState.selectedBranch.addressLat,
            currentState.selectedBranch.addressLng
        )

        val userBranchDistance = if (userLocation != null) {
            calculateDistanceInKilometers(
                fromLocation = userLocation,
                toLocation = branchLocation
            )
        } else { 0.0 }

        val selectedLocationBranchDistance = if (userManuallySelectedLocation != null) {
            calculateDistanceInKilometers(
                fromLocation = userManuallySelectedLocation,
                toLocation = branchLocation
            )
        } else { 0.0 }
        setState {
            copy(
                distanceBetweenUserAndBranchInKm = userBranchDistance,
                distanceBetweenSelectedLocationAndBranchInKm = selectedLocationBranchDistance
            )
        }
    }

    private fun onSelectLocationAddressChanged(event: BranchAddressEvent.OnSelectedLocationAddressChanged) {
        val address = event.address
        if (address == null) {
            setState { copy(selectedLocationAddressText = DeferredString.StringResource(R.string.address_not_found)) }
            return
        }
        val refinedAddress = DeferredString.StringValue(
            listOfNotNull(
                address.state,
                address.city,
                address.streetName,
            ).joinToString(", ")
        )
        setState {
            copy(
                selectedLocationAddress = address,
                selectedLocationAddressText = refinedAddress,
            )
        }
    }

    private fun onEditSelectedLocation() {
        setState { copy(isAddingNewAddress = false) }
    }

    private fun onAddNewAddressClicked() {
        setState { copy(isAddingNewAddress = true) }
        val location = if (currentState.userManuallySelectedLocation == null) {
            if (currentState.isLocationPermissionGranted) {
                currentState.userCurrentLocation ?: currentState.defaultMapCenterLocation
            } else {
                showPermissionDeniedAlert()
                return
            }
        } else {
            currentState.userManuallySelectedLocation!!
        }
        updateMapCameraPosition(
            location = location,
            zoom = 14f,
        )
        getSelectedAddressFromLocation(location = location)
    }

    private fun onSelectDeliveryAddress(event: BranchAddressEvent.OnSelectDeliveryAddress) {
        when (event.address.addressType) {
            AddressType.HOME.value -> setState {
                copy(
                    homeAddress = event.address,
                    isHomeSelected = true,
                )
            }

            AddressType.WORK.value -> setState {
                copy(
                    workAddress = event.address,
                    isHomeSelected = false,
                )
            }

            else -> {}
        }
        setState {
            copy(
                userManuallySelectedLocation = event.address.let {
                    LatLng(it.latitude, it.longitude)
                },
            )
        }

        updateMapCameraPosition(
            location = event.address.let { LatLng(it.latitude, it.longitude) },
            zoom = 14f,
        )
    }

    private fun onConfirmDeliveryAddressSelection() {
        val selectedAddress = if (currentState.isHomeSelected) {
            currentState.homeAddress
        } else {
            currentState.workAddress
        }
        if (selectedAddress != null) {
            setState {
                copy(
                    userManuallySelectedLocation = LatLng(
                        selectedAddress.latitude,
                        selectedAddress.longitude
                    )
                )
            }
            sendEffect(
                BranchAddressEffect.NavigateBackWithResult(
                    result = BranchAddressResult(
                        deliveryAddress = selectedAddress,
                    )
                )
            )
        }
    }

    private fun onConfirmAddingNewAddress() {
        if (currentState.isValidForCreatingOrUpdatingCustomerAddress()) {
            if (
                (currentState.selectedAddressType == AddressType.HOME && currentState.homeAddress != null) ||
                (currentState.selectedAddressType == AddressType.WORK && currentState.workAddress != null)
            ) {
                updateCustomerAddress()
            } else {
                createNewCustomerAddress()
            }
        } else {
            val dialogState = ErrorDialogState.default.copy(
                message = DeferredString.StringResource(R.string.address_details_error),
                primaryButtonText = DeferredString.StringResource(R.string.ok),
            )
            sendEffect(BranchAddressEffect.ShowError(dialogState))
        }
    }

    private fun updateCustomerAddress() {
        setState { copy(isLoading = true) }
        launchSafe {
            val request = currentState.toUpdateCustomerAddressRequest()
            val isSuccess = updateCustomerAddressUseCase(request)
            if (isSuccess) {
                val addresses = getCustomerAddressesUseCase()
                sendEvent(
                    BranchAddressEvent.LoadArgs(
                        args = currentState.args!!.copy(
                            customerAddresses = addresses
                        )
                    )
                )
                setState {
                    copy(
                        isAddingNewAddress = false,
                        isLoading = false
                    )
                }
            } else {
                setState { copy(isLoading = false) }
                val dialogState = ErrorDialogState.default.copy(
                    onPrimaryBtnClicked = { onConfirmAddingNewAddress() }
                )
                sendEffect(BranchAddressEffect.ShowError(dialogState))
            }
        }
    }

    private fun createNewCustomerAddress() {
        setState { copy(isLoading = true) }
        launchSafe {
            val request = currentState.toCreateCustomerAddressRequest()
            val isSuccess = createCustomerAddressUseCase(request)
            if (isSuccess) {
                val addresses = getCustomerAddressesUseCase()
                val newAddressId = addresses.find {
                    it.addressType == currentState.selectedAddressType.value
                }?.id ?: ""

                val newAddress = request.toCustomerAddress(addressId = newAddressId)
                val isHome = currentState.selectedAddressType == AddressType.HOME
                if (isHome) {
                    setState { copy(homeAddress = newAddress) }
                } else {
                    setState { copy(workAddress = newAddress) }
                }
                sendEvent(
                    BranchAddressEvent.LoadArgs(
                        args = currentState.args!!.copy(
                            customerAddresses = addresses
                        )
                    )
                )
                setState {
                    copy(
                        isAddingNewAddress = false,
                        isLoading = false
                    )
                }
            } else {
                setState { copy(isLoading = false) }
                val dialogState = ErrorDialogState.default.copy(
                    onPrimaryBtnClicked = { onConfirmAddingNewAddress() }
                )
                sendEffect(BranchAddressEffect.ShowError(dialogState))
            }
        }
    }

    private fun onConfirmBranchSelection() {
        sendEffect(
            BranchAddressEffect.NavigateBackWithResult(
                result = BranchAddressResult(
                    selectedBranch = currentState.selectedBranch,
                )
            )
        )
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }
        val errorMessage = if (throwable.message.isNullOrEmpty()) ErrorDialogState.default.message
        else DeferredString.StringValue(throwable.message!!)

        sendEffect(
            BranchAddressEffect.ShowError(
                dialogState = ErrorDialogState.default.copy(
                    message = errorMessage,
                    primaryButtonText = DeferredString.StringResource(R.string.ok),
                )
            )
        )
    }

}
