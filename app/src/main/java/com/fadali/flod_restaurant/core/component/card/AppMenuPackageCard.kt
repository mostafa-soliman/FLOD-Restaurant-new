package com.fadali.flod_restaurant.core.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.core.component.dashedline.DashedDivider
import com.fadali.flod_restaurant.core.component.image.AppNetworkImage
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

enum class PackageCardType {
    SUBSCRIPTION_ITEM,
    SUBSCRIPTION_SUMMARY
}

@Composable
fun AppMenuPackageCard(
    modifier: Modifier = Modifier,
    imageUrl: Any,
    title: String,
    subtitle: String? = null,
    buttonText: String,
    onButtonClick: () -> Unit,
    cardType: PackageCardType,
    dateRange: String? = null,
    daysCount: String? = null,
    statusLabel: String? = null,
    deliveryLabel: String? = null,
    mealsCount: String? = null,
    outerPadding: Dp = 16.dp,
    isPickup: Boolean,
    showNutrientCard: Boolean = false,
    nutrientValues: List<Pair<String, NutrientType>> = emptyList(),
    showTimeline: Boolean = false,
    timelineStartLabel: String? = null,
    timelineEndLabel: String? = null,
    timelineDotColor: Color = Color(0xFF0AA6B5)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFFECECEC), shape = RoundedCornerShape(size = 15.dp))
            .padding(outerPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AppNetworkImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .weight(if (subtitle.isNullOrEmpty()) 1f else 0.6f)
                        .padding(end = 8.dp),
                            style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!subtitle.isNullOrEmpty()) {
                    Text(
                        text = subtitle,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF8B0000)
                        ),
                        modifier = Modifier
                            .weight(0.4f)
                            .background(
                                color = Color(0x33FF5722),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            if (cardType == PackageCardType.SUBSCRIPTION_SUMMARY) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar_outline),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = dateRange ?: "",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        )
                    }
                    Text(
                        text = daysCount ?: "",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    )
                }

                // Timeline section (using same pattern as PlanMenuScreen)
                if (showTimeline && (!timelineStartLabel.isNullOrEmpty() || !timelineEndLabel.isNullOrEmpty())) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.width(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Top dot
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(timelineDotColor, CircleShape)
                                )
                                // Dashed line
                                DashedDivider(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(30.dp),
                                    color = MaterialTheme.colorScheme.secondary,
                                    thickness = 1.dp,
                                    dashLength = 4.dp,
                                    gapLength = 4.dp
                                )
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(timelineDotColor, CircleShape)
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(11.dp)
                            ) {
                                timelineStartLabel?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = timelineDotColor
                                        )
                                    )
                                }
                                timelineEndLabel?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = timelineDotColor
                                        )
                                    )
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .width(20.dp)
                                .height(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = if (isPickup) R.drawable.ic_pickup else R.drawable.ic_delivery_car),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
            if (showNutrientCard) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    nutrientValues.forEach { (value, type) ->
                        AppNutrientCard(
                            value = value,
                            nutrientType = type
                        )
                    }
                }
            } else {
                AppPrimaryButton(
                    onClick = onButtonClick,
                    text = buttonText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                )
            }
        }
    }
}

@AppPreviews
@Composable
private fun AppMenuPackageCardPreview() {
    FLODRestaurantTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Subscription Package - Basic
            AppMenuPackageCard(
                imageUrl = R.drawable.startingdate_img,
                title = "Variety Package",
                buttonText = "Freeze Package",
                onButtonClick = {},
                cardType = PackageCardType.SUBSCRIPTION_SUMMARY,
                dateRange = "01 May - 30 May",
                daysCount = "24 D",
                isPickup = true
            )

            // Subscription Package - With Timeline
            AppMenuPackageCard(
                imageUrl = R.drawable.startingdate_img,
                title = "Premium Package",
                buttonText = "Freeze Package",
                onButtonClick = {},
                cardType = PackageCardType.SUBSCRIPTION_SUMMARY,
                dateRange = "20 Days plan",
                daysCount = "x3 Meals",
                isPickup = false,
                showTimeline = true,
                timelineStartLabel = "Start: 01 May",
                timelineEndLabel = "End: 15 May"
            )

            // Meal - Basic
            AppMenuPackageCard(
                imageUrl = R.drawable.startingdate_img,
                title = "Egg Dish",
                subtitle = "Meal No (3)",
                buttonText = "Customize",
                onButtonClick = {},
                cardType = PackageCardType.SUBSCRIPTION_ITEM,
                isPickup = false
            )

            // Meal - With Nutrients
            AppMenuPackageCard(
                imageUrl = R.drawable.startingdate_img,
                title = "Grilled Chicken",
                subtitle = "Meal No (1)",
                buttonText = "Customize",
                onButtonClick = {},
                cardType = PackageCardType.SUBSCRIPTION_ITEM,
                isPickup = false,
                showNutrientCard = true,
                nutrientValues = listOf(
                    "200" to NutrientType.CALORIES,
                    "150g" to NutrientType.PROTEIN,
                    "30g" to NutrientType.CARBS,
                    "35g" to NutrientType.FAT
                )
            )
        }
    }
}
