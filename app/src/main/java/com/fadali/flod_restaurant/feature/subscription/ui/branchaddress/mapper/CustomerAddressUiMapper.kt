package com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.mapper

import com.fadali.flod_restaurant.core.network.appconfig.AddressType
import com.fadali.flod_restaurant.core.util.isNotBlackOrEmpty
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CreateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.UpdateCustomerAddressRequest
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressState

fun CreateCustomerAddressRequest.toCustomerAddress(addressId: String) = CustomerAddress(
    id = addressId,
    addressType = addressType,
    streetName = streetName,
    buildingName = buildingName,
    flatNumber = flatNumber,
    city = city,
    district = district,
    landmark = landmark,
    additionalNotes = additionalNotes,
    latitude = latitude,
    longitude = longitude,
)

fun BranchAddressState.isValidForCreatingOrUpdatingCustomerAddress() =
    streetName.trim().isNotBlackOrEmpty() &&
            buildingName.trim().isNotBlackOrEmpty() &&
            flatNumber.trim().isNotBlackOrEmpty() &&
            nearestSign.trim().isNotBlackOrEmpty() &&
            userManuallySelectedLocation != null

fun BranchAddressState.toCreateCustomerAddressRequest() = CreateCustomerAddressRequest(
    addressType = selectedAddressType.value,
    streetName = streetName.trim(),
    buildingName = buildingName.trim(),
    flatNumber = flatNumber.trim().toInt(),
    city = selectedLocationAddress?.state ?: "",
    district = selectedLocationAddress?.city ?: "",
    landmark = nearestSign.trim(),
    additionalNotes = null,
    latitude = userManuallySelectedLocation?.latitude ?: 0.0,
    longitude = userManuallySelectedLocation?.longitude ?: 0.0,
)

fun BranchAddressState.toUpdateCustomerAddressRequest() = UpdateCustomerAddressRequest(
    id = if (selectedAddressType.value == AddressType.HOME.value) homeAddress!!.id else workAddress!!.id,
    addressType = selectedAddressType.value,
    streetName = streetName.trim(),
    buildingName = buildingName.trim(),
    flatNumber = flatNumber.trim().toInt(),
    city = selectedLocationAddress?.state ?: "",
    district = selectedLocationAddress?.city ?: "",
    landmark = nearestSign.trim(),
    additionalNotes = null,
    latitude = userManuallySelectedLocation?.latitude ?: 0.0,
    longitude = userManuallySelectedLocation?.longitude ?: 0.0,
)

