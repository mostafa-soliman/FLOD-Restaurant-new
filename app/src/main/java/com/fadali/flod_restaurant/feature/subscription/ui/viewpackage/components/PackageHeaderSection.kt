package com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.image.SaudiRiyalIcon

@Composable
fun PackageHeaderSection(
    packageTitle: String,
    packageSubtitle: String,
    packagePrice: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = packageTitle.uppercase(),
            modifier = Modifier.weight(1.7f),
            style = typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
        )
        Row(
            Modifier
                .weight(1f)
                .padding(end = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            SaudiRiyalIcon(
                tint = colorScheme.primary
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = stringResource(R.string.price_sar, packagePrice),
                style = typography.titleMedium.copy(
                    color = colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
    Spacer(Modifier.height(8.dp))
    Text(
        text = packageSubtitle,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight(600),
            color = colorScheme.primary,
        )
    )
}

