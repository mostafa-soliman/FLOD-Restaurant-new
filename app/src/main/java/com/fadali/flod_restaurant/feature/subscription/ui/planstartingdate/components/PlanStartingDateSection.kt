package com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.card.AppDashedChoice
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun PlanStartingDateSection(
    modifier: Modifier = Modifier,
    selectedDateIndex: Int,
    onDurationSelected: (Int) -> Unit,
    startingDates: List<String>
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.plan_starting_date),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            startingDates.forEachIndexed { index, duration ->
                AppDashedChoice(
                    selected = selectedDateIndex == index,
                    onClick = { onDurationSelected(index) },
                    modifier = Modifier
                        .heightIn(min = 36.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Row(
                        Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar_outline),
                            modifier = Modifier.sizeIn(
                                minWidth = 15.dp,
                                minHeight = 15.dp
                            ),
                            contentDescription = null,
                            tint = if (selectedDateIndex == index) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = duration,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (selectedDateIndex == index) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface
                            ),
                            fontWeight = if (selectedDateIndex == index) FontWeight.SemiBold else FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@AppPreviews
@Composable
private fun PlanStartingDateSectionPreview() {
    FLODRestaurantTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(20.dp)
        ) {
            PlanStartingDateSection(
                selectedDateIndex = 1,
                onDurationSelected = {},
                startingDates = listOf("12 Aug", "13 Aug", "14 Aug", "15 Aug", "16 Aug")
            )
        }
    }
}
