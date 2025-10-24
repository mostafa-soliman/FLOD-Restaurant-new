package com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.dashedline.DashedDivider
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun DeliveryTimeSelectionSection(
    selectedTimeSlotIndex: Int,
    onTimeSlotSelected: (Int) -> Unit,
    timeSlots: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
    ) {
        Text(
            text = stringResource(R.string.choose_delivery_time),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        )

        timeSlots.forEachIndexed { index, slot ->
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onTimeSlotSelected(index) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedTimeSlotIndex == index,
                        onClick = { onTimeSlotSelected(index) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                    Text(
                        text = timeSlots[index],
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (selectedTimeSlotIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                if (index != timeSlots.lastIndex) {
                    DashedDivider(
                        modifier = Modifier
                            .width(2.dp)
                            .height(23.dp)
                            .offset(x = 23.dp, y = 38.dp), // Offset to center under RadioButton
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 2.dp,
                        dashLength = 3.dp,
                        gapLength = 3.dp,
                        isVertical = true
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DeliveryTimeSelectionSectionPreview() {
    FLODRestaurantTheme {
        DeliveryTimeSelectionSection(
            selectedTimeSlotIndex = 0,
            onTimeSlotSelected = {},
            timeSlots = listOf("10:00 AM - 12:00 PM", "12:00 PM - 02:00 PM")
        )
    }
}
