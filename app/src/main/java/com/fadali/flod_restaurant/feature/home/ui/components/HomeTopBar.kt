package com.fadali.flod_restaurant.feature.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.image.AppNetworkImage
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun HomeTopBar(userName: String, avatarModel: Any) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Notifications, contentDescription = null, tint = colorScheme.primary)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.hello_user, userName),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(Modifier.width(8.dp))
            AppNetworkImage(
                model = avatarModel,
                modifier = Modifier.size(50.dp),
                shape = CircleShape
            )
        }
    }
}

@AppPreviews
@Composable
private fun SubscriptionHomeTopBarPreview() {
    FLODRestaurantTheme {
        HomeTopBar(
            userName = "Fadali",
            avatarModel = R.drawable.ic_avatar_img
        )
    }
}
