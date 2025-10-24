package com.fadali.flod_restaurant.core.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.core.component.image.AppNetworkImage
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun AppMealPlanHorizontalCard(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    name: String,
    quantity: String? = null
) {
    Card(
        modifier = modifier
            .height(60.dp)
            .width(180.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(15.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppNetworkImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = 1
                )
                if (!quantity.isNullOrBlank()) {
                    Text(
                        text = quantity,
                        style = TextStyle(
                            fontSize = 9.35.sp,
                            fontWeight = FontWeight(500),
                            color = MaterialTheme.colorScheme.primary
                        ),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealPlanHorizontalCardPreview() {
    FLODRestaurantTheme {
        AppMealPlanHorizontalCard(
            imageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b",
            name = "Grilled Chicken"
        )
    }
}

@Preview(showBackground = true, name = "Multiple Cards")
@Composable
fun MealPlanHorizontalCardsPreview() {
    FLODRestaurantTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppMealPlanHorizontalCard(
                imageUrl = "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe",
                name = "Avocado Salad",
                quantity = "x2 Meals"
            )
            AppMealPlanHorizontalCard(
                imageUrl = "https://images.unsplash.com/photo-1504674900247-0877df9cc836",
                name = "Grilled Chicken with Rice",
                quantity = "x3 Meals"
            )
            AppMealPlanHorizontalCard(
                imageUrl = "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445",
                name = "Salmon Dish",
                quantity = "x1 Meal"
            )
        }
    }
}

