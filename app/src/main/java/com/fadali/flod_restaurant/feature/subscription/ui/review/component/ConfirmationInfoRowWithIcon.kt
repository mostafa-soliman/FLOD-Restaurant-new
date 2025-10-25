package com.fadali.flod_restaurant.feature.subscription.ui.review.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

@Composable
fun ConfirmationInfoRowWithIcon(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: Painter
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label, style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.appText.copy(alpha = 0.6f),
                textAlign = TextAlign.Start
            )
        )
        Spacer(modifier = Modifier.size(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.appText,
                    textAlign = TextAlign.End
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Image(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .requiredSize(16.dp),
                painter = icon,
                contentDescription = null
            )
        }
    }
}

@AppPreviews
@Composable
private fun ConfirmationInfoRowWithIconsPreview() {
    FLODRestaurantTheme {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(20.dp)
        ) {
            ConfirmationInfoRowWithIcon(
                label = "Pickup branch",
                value = "Riyadh, Al olaya...",
                icon = painterResource(id = R.drawable.ic_location)
            )
            ConfirmationInfoRowWithIcon(
                label = "Delivery address",
                value = "Home address",
                icon = painterResource(id = R.drawable.ic_location)
            )
        }
    }
}