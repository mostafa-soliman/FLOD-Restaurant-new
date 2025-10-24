package com.fadali.flod_restaurant.feature.subscription.ui.review.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.ConfirmationInfo
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun ConfirmationInfoSection(
    modifier: Modifier = Modifier,
    isPickup: Boolean,
    confirmationInfo: ConfirmationInfo
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ConfirmationInfoRow(
            label = stringResource(R.string.meals_per_day3),
            value = stringResource(R.string.subscription_meal_text, confirmationInfo.mealsPerDay)
        )
        ConfirmationInfoRow(
            label = stringResource(R.string.number_of_days),
            value = stringResource(R.string.subscription_duration_text, confirmationInfo.numberOfDays)
        )
        ConfirmationInfoRow(
            label = stringResource(R.string.starting_date),
            value = confirmationInfo.startingDate
        )
        if (isPickup) {
            ConfirmationInfoRowWithIcon(
                label = stringResource(R.string.pickup_branch),
                value = confirmationInfo.pickUpBranchAddress,
                icon = painterResource(id = R.drawable.ic_location)
            )
        } else {
            ConfirmationInfoRow(
                label = stringResource(R.string.delivery_time_slot),
                value = confirmationInfo.deliveryTimeSlot
            )
            ConfirmationInfoRowWithIcon(
                label = stringResource(R.string.delivery_address),
                value = confirmationInfo.deliveryAddress,
                icon = painterResource(id = R.drawable.ic_location)
            )
            ConfirmationInfoRowWithIcon(
                label = stringResource(R.string.street_name),
                value = confirmationInfo.streetName,
                icon = painterResource(id = R.drawable.ic_nav_home)
            )
            ConfirmationInfoRowWithIcon(
                label = stringResource(R.string.building_number),
                value = confirmationInfo.buildingNumber,
                icon = painterResource(id = R.drawable.ic_nav_home)
            )
            ConfirmationInfoRow(
                label = stringResource(R.string.flat_number),
                value = confirmationInfo.flatNumber
            )
            ConfirmationInfoRow(
                label = stringResource(R.string.phone_number),
                value = confirmationInfo.phoneNumber
            )
        }
    }
}

@AppPreviews
@Composable
private fun ConfirmationInfoSectionPreview() {
    FLODRestaurantTheme {
        ConfirmationInfoSection(
            modifier = Modifier
                .statusBarsPadding()
                .padding(20.dp),
            isPickup = true,
            confirmationInfo = ConfirmationInfo(
                mealsPerDay = 3,
                numberOfDays = 7,
                startingDate = "20 Aug, 2024",
                deliveryTimeSlot = "10:00:00 - 23:00:00",
                deliveryAddress = "City, District, Street",
                streetName = "Main St",
                buildingNumber = "12B",
                flatNumber = "34",
                phoneNumber = "0512345678",
                pickUpBranchAddress = "Branch Address"
            )
        )
    }
}
