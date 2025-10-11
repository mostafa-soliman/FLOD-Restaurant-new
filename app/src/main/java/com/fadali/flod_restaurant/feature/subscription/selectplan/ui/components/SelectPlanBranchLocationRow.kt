package com.fadali.flod_restaurant.feature.subscription.selectplan.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun SubscriptionBranchLocationRow(
    modifier: Modifier = Modifier,
    branchAddress: String,
    onEditBranchLocation: () -> Unit
) {
    val materialColorScheme = MaterialTheme.colorScheme

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_location),
            contentDescription = null,
            tint = materialColorScheme.secondary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = branchAddress,
            style = MaterialTheme.typography.titleMedium.copy(
                color = materialColorScheme.secondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            modifier = Modifier.clickable { onEditBranchLocation() },
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = null,
            tint = materialColorScheme.primary
        )
    }
}

@AppPreviews
@Composable
fun SubscriptionBranchLocationRowPreview() {
    FLODRestaurantTheme {
        SubscriptionBranchLocationRow(
            branchAddress = "123 Main St, Springfield, IL 62701, USA",
            onEditBranchLocation = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}