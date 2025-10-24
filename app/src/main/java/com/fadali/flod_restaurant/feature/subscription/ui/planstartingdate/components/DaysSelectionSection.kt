package com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.network.appconfig.DayOfWeek
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.util.Dimens

@Composable
fun DaysSelectionSection(
    modifier: Modifier = Modifier,
    selectedDays: Set<DayOfWeek>,
    onDaysChanged: (Set<DayOfWeek>) -> Unit,
    daysOfWeek: List<DayOfWeek>,
    isPickup: Boolean
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(
                if (isPickup) R.string.select_pickup_days else R.string.select_delivery_days
            ),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            verticalArrangement = Arrangement.spacedBy(Dimens.CardSpacing)
        ) {
            FlowRow (
                verticalArrangement = Arrangement.spacedBy(Dimens.CardSpacing),
                horizontalArrangement = Arrangement.spacedBy(Dimens.CardSpacing)
            ) {
                daysOfWeek.forEach { day ->
                    PickupDaySelectionPill(
                        dayName = stringResource(day.displayName),
                        isSelected = day in selectedDays,
                        onDayClick = {
                            onDaysChanged(
                                if (day in selectedDays) {
                                    selectedDays - day
                                } else {
                                    selectedDays + day
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

@AppPreviews
@Composable
private fun DaysSelectionSectionPreview() {
    FLODRestaurantTheme {
        DaysSelectionSection(
            modifier = Modifier
                .statusBarsPadding()
                .padding(20.dp),
            selectedDays = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
            onDaysChanged = {},
            daysOfWeek = DayOfWeek.entries,
            isPickup = false
        )
    }
}
