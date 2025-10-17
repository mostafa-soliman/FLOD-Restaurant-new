package com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.util.MapsUtils.getBitmapDescriptorFromDrawable
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    userLocation: LatLng?,
    userManuallySelectedLocation: LatLng?,
    restaurantBranches: List<Branch>,
    isLocationPermissionGranted: Boolean,
    selectedBranch: Branch,
    onMapClick: (LatLng) -> Unit,
    onBranchClick: (Branch) -> Unit
) {
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = false,
            compassEnabled = false,
        ),
        properties = MapProperties(
            isMyLocationEnabled = isLocationPermissionGranted
        ),
        onMapClick = onMapClick
    ) {
        val activeUserLocation = userManuallySelectedLocation ?: userLocation
        activeUserLocation?.let { location ->
            val title = if (userManuallySelectedLocation != null) {
                stringResource(R.string.selected_location_marker_title)
            } else {
                stringResource(R.string.current_location_marker_title)
            }
            val snippet = if (userManuallySelectedLocation != null) {
                stringResource(R.string.selected_location_marker_snippet)
            } else {
                stringResource(R.string.current_location_marker_snippet)
            }
            Marker(
                state = MarkerState(location),
                title = title,
                snippet = snippet,
                icon = getBitmapDescriptorFromDrawable(R.drawable.ic_locationpin)
            )
        }
        restaurantBranches.forEach { branch ->
            val isSelectedBranch = branch.id == selectedBranch.id
            Marker(
                state = MarkerState(LatLng(branch.addressLat, branch.addressLng)),
                title = branch.displayName,
                icon = if (isSelectedBranch) {
                    getBitmapDescriptorFromDrawable(R.drawable.ic_activelocation)
                } else {
                    getBitmapDescriptorFromDrawable(R.drawable.ic_nonactivelocation)
                },
                onClick = {
                    onBranchClick(branch)
                    true
                }
            )
        }
        activeUserLocation?.let { location ->
            Polyline(
                points = listOf(
                    LatLng(selectedBranch.addressLat, selectedBranch.addressLng),
                    location
                ),
                color = MaterialTheme.colorScheme.primary,
                width = 8f
            )
        }
    }
}
