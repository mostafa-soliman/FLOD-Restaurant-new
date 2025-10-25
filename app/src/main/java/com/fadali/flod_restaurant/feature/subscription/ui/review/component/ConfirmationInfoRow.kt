package com.fadali.flod_restaurant.feature.subscription.ui.review.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

@Composable
fun ConfirmationInfoRow(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label, style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.appText.copy(alpha = 0.6f),
                textAlign = TextAlign.Start
            )
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.appText,
                textAlign = TextAlign.End
            )
        )
    }
}

@AppPreviews
@Composable
private fun ConfirmationInfoRowsPreview() {
    FLODRestaurantTheme {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(20.dp)
        ) {
            ConfirmationInfoRow(
                label = "Meals per day", value = "x3 Meals"
            )
            ConfirmationInfoRow(
                label = "Number of days", value = "7 Days"
            )
            ConfirmationInfoRow(
                label = "Starting date", value = "25 Aug"
            )
        }
    }
}