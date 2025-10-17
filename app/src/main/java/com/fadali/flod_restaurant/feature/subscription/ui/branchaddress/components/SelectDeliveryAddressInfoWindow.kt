package com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.utils.DashedDivider
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

@Composable
fun SelectDeliveryAddressInfoWindow(
    modifier: Modifier = Modifier,
    homeAddress: CustomerAddress?,
    workAddress: CustomerAddress?,
    isHomeSelected: Boolean,
    onAddressSelected: (CustomerAddress) -> Unit,
    onAddNewAddressClicked: () -> Unit,
    onConfirmClicked: () -> Unit,
    onRequestCurrentUserLocation: () -> Unit,
) {
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
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.delivery_address),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    TextButton(
                        onClick = onAddNewAddressClicked
                    ) {
                        Text(
                            text = stringResource(R.string.new_address),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                }
                if (homeAddress != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_address_home),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = stringResource(R.string.address_home),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                text = homeAddress.displayName,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.appText.copy(alpha = 0.7f),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                            )
                        }
                        RadioButton(
                            selected = isHomeSelected,
                            onClick = { onAddressSelected(homeAddress) }
                        )
                    }
                }
                if (homeAddress != null && workAddress != null) {
                    DashedDivider(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .width(2.dp)
                            .height(30.dp)
                    )
                }
                if (workAddress != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_address_work),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = stringResource(R.string.address_work),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                text = workAddress.displayName,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.appText.copy(alpha = 0.7f),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                            )
                        }
                        RadioButton(
                            selected = isHomeSelected.not(),
                            onClick = { onAddressSelected(workAddress) }
                        )
                    }
                }
                AppPrimaryButton(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(R.string.confirm),
                    onClick = onConfirmClicked,
                    enabled = homeAddress != null || workAddress != null
                )
            }
        }
    }
}

@Preview
@Composable
private fun SelectDeliveryAddressInfoWindowPreview() {
    FLODRestaurantTheme {
        SelectDeliveryAddressInfoWindow(
            homeAddress = CustomerAddress.empty.copy(displayName = "123 Main St, City, Country"),
            workAddress = CustomerAddress.empty.copy(displayName = "456 Work Rd, City, Country"),
            isHomeSelected = true,
            onAddressSelected = {},
            onAddNewAddressClicked = {},
            onConfirmClicked = {},
            onRequestCurrentUserLocation = {}
        )
    }
}
