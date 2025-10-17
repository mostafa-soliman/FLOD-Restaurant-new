package com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.util.MapsUtils.getMapsUri
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.google.android.gms.maps.model.LatLng

@Composable
fun BranchInfoWindowPickup(
    modifier: Modifier = Modifier,
    branchName: String,
    branchAddress: String,
    distance: String,
    branchCoordinates: LatLng,
    onConfirmSelection: () -> Unit,
    isLocationPermissionEnabled: Boolean,
    onRequestLocationPermission: () -> Unit,
    onRequestCurrentUserLocation: () -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        FloatingActionButton (
            modifier = Modifier
                .padding(bottom = 16.dp, end = 20.dp)
                .align(Alignment.End),
            onClick = onRequestCurrentUserLocation,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = stringResource(R.string.current_location_content_desc)
            )
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = 0.dp,
            shadowElevation = 10.dp,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.branch_address),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = branchAddress,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xD1000000),
                            )
                        )
                        Text(
                            text = distance,
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    IconButton(
                        onClick = {
                            val googleMapsUri = getMapsUri(
                                latitude = branchCoordinates.latitude,
                                longitude = branchCoordinates.longitude,
                                label = branchName
                            )
                            context.startActivity(
                                Intent(Intent.ACTION_VIEW, googleMapsUri).apply {
                                    setPackage("com.google.android.apps.maps")
                                    flags = FLAG_ACTIVITY_NEW_TASK
                                }
                            )
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_open_in_maps),
                            contentDescription = "Open in Google Maps",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Button(
                    onClick = onConfirmSelection,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) {
                    Text(
                        stringResource(R.string.confirm),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
                if (!isLocationPermissionEnabled) {
                    TextButton(
                        onClick = onRequestLocationPermission,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(stringResource(R.string.enable_location_permissions))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BranchInfoWindowPreview() {
    FLODRestaurantTheme {
        BranchInfoWindowPickup(
            branchName = "Main Branch",
            branchAddress = "123 Main St, City, Country",
            distance = "2.5 km",
            branchCoordinates = LatLng(37.7749, -122.4194),
            onConfirmSelection = {},
            isLocationPermissionEnabled = false,
            onRequestLocationPermission = {},
            onRequestCurrentUserLocation = {}
        )
    }
}
