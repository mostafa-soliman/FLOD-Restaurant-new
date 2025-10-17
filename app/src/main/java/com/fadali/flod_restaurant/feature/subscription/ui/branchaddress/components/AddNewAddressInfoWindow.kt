package com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.core.component.multiselect.SegmentedTwoButtonsAddress
import com.fadali.flod_restaurant.core.component.textfield.AppTextField
import com.fadali.flod_restaurant.core.network.appconfig.AddressType
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

@Composable
fun AddNewAddressInfoWindow(
    modifier: Modifier = Modifier,
    selectedAddress: String,
    selectedAddressType: AddressType,
    onAddressTypeSelected: (addressType: AddressType) -> Unit,
    onEditSelectedAddressClicked: () -> Unit,
    streetName: String,
    buildingName: String,
    flatNumber: String,
    nearestSign: String,
    onStreetNameChanged: (String) -> Unit,
    onBuildingNameChanged: (String) -> Unit,
    onFlatNumberChanged: (String) -> Unit,
    onNearestSignChanged: (String) -> Unit,
    onConfirmClicked: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
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
            Text(
                text = stringResource(R.string.delivery_address),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
            )
            SegmentedTwoButtonsAddress(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                leftLabel = stringResource(R.string.home),
                rightLabel = stringResource(R.string.work),
                leftIcon = ImageVector.vectorResource(R.drawable.ic_address_home),
                rightIcon = ImageVector.vectorResource(R.drawable.ic_address_work),
                selectedLeft = selectedAddressType == AddressType.HOME,
                onSelect = { type ->
                    onAddressTypeSelected(type)
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_location),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = selectedAddress,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.appText.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { onEditSelectedAddressClicked() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = stringResource(R.string.edit_address),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = streetName,
                onValueChange = onStreetNameChanged,
                label = stringResource(R.string.street_name),
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = buildingName,
                onValueChange = onBuildingNameChanged,
                label = stringResource(R.string.building_name_number),
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = flatNumber,
                onValueChange = onFlatNumberChanged,
                label = stringResource(R.string.flat_number),
                keyboardType = KeyboardType.Number,
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = nearestSign,
                onValueChange = onNearestSignChanged,
                label = stringResource(R.string.nearest_sign_or_address),
            )
            AppPrimaryButton(
                modifier = Modifier.padding(top = 10.dp),
                text = stringResource(R.string.confirm),
                onClick = onConfirmClicked
            )
        }
    }
}

@Preview
@Composable
private fun AddNewAddressInfoWindowPreview() {
    FLODRestaurantTheme {
        AddNewAddressInfoWindow(
            selectedAddress = "Main Street, City, Country",
            selectedAddressType = AddressType.HOME,
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
        )
    }
}
