package com.fadali.flod_restaurant.feature.home.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.core.component.image.AppNetworkImage
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun PlanCard(
    imageUrl: Any,
    title: String,
    ratingText: String,
    actionText: String,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    onCardClick: (() -> Unit)? = null
) {
    val shape = RoundedCornerShape(6.dp)
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .height(181.dp)
            .clip(RoundedCornerShape(6.dp))
            .then(
                if (onCardClick != null) Modifier.clickable { onCardClick() } else Modifier
            )
    ) {
        AppNetworkImage(
            model = imageUrl,
            shape = shape,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(12.dp)
                .height(48.dp)
        ) {
            BlurScrim(
                Modifier.matchParentSize(),
                scrimColor = Color.Black.copy(alpha = 0.4f)
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        title,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 16.sp,
                            color = colorScheme.onPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        ratingText,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = colorScheme.onPrimary,
                            fontSize = 8.sp
                        )
                    )
                }
                Button(
                    onClick = onActionClick,
                    modifier = Modifier
                        .height(30.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary
                    ),
                    contentPadding = PaddingValues(
                        start = 10.dp,
                        top = 4.dp,
                        end = 10.dp,
                        bottom = 4.dp
                    )
                ) {
                    Text(
                        actionText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colorScheme.onPrimary,
                        ),
                    )
                }
            }
        }
    }
}

@AppPreviews
@Composable
private fun SubscriptionPlanCardPreview() {
    FLODRestaurantTheme {
        PlanCard(
            imageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b",
            title = "Margherita Pizza",
            ratingText = "⭐ 4.8 (120 reviews)",
            actionText = "Order Now",
            onActionClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}