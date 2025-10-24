package com.fadali.flod_restaurant.core.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.core.component.image.AppNetworkImage
import com.fadali.flod_restaurant.core.component.image.SaudiRiyalIcon
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.nutrientColors

@Composable
fun AppAddOnCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    calories: Int?,
    price: Double,
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    val counterSize = 17.dp
    val displayedPrice = price * quantity
    Card(
        modifier = modifier.clickable {
            if (quantity == 0) {
                onQuantityChange(1)
            }
        },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x99DFDFDF)),
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppNetworkImage(
                modifier = Modifier
                    .size(41.dp)
                    .clip(CircleShape),
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Row(
                            Modifier.padding(horizontal = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SaudiRiyalIcon(
                                modifier = Modifier.widthIn(min = 12.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = displayedPrice.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.padding(
                        end = 3.dp,
                        top = 6.dp
                    ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "🔥 $calories Cal",
                        modifier = Modifier
                            .alpha(if (calories != null) 1f else 0f)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(
                                horizontal = 4.dp,
                                vertical = 3.dp
                            ),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.nutrientColors.calories,
                        ),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.padding(start = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .widthIn(min = counterSize)
                                .heightIn(min = counterSize)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                                .clickable { if (quantity > 0) onQuantityChange(quantity - 1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "-",
                                modifier = Modifier.align(Alignment.Center),
                                style = TextStyle(
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.surface
                                )
                            )
                        }
                        Text(
                            text = quantity.toString(),
                            modifier = Modifier.align(Alignment.CenterVertically),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Box(
                            modifier = Modifier
                                .widthIn(min = counterSize)
                                .heightIn(min = counterSize)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                                .clickable { onQuantityChange(quantity + 1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+",
                                modifier = Modifier.align(Alignment.Center),
                                style = TextStyle(
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.surface
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@AppPreviews
@Composable
private fun CoreAddOnCardPreviewRow() {
    FLODRestaurantTheme {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(20.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            var quantity1 by remember { mutableIntStateOf(3) }
            var quantity2 by remember { mutableIntStateOf(0) }
            AppAddOnCard(
                imageUrl = "https://images.unsplash.com/photo-1563729781350-f80e920d3f11?w=1200",
                name = "vanilla ice cream",
                calories = 200,
                price = 5.0,
                quantity = quantity1,
                onQuantityChange = { quantity1 = it }
            )
            AppAddOnCard(
                imageUrl = "https://images.unsplash.com/photo-1560000021-3e4b7b252d0a?w=1200",
                name = "cup cake",
                calories = null,
                price = 10.0,
                quantity = quantity2,
                onQuantityChange = { quantity2 = it }
            )
        }
    }
}
