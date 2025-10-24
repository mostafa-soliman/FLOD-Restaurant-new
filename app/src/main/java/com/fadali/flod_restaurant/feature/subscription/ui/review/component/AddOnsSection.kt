package com.fadali.flod_restaurant.feature.subscription.ui.review.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.card.AppAddOnCard
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.feature.subscription.domain.model.addons.AddonNutritionInfo
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.AddonUiModel
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun AddOnsSection(
    addOns: List<AddonUiModel>,
    onQuantityChange: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.would_you_like_some_addons),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(addOns) { index, addon ->
                AppAddOnCard(
                    imageUrl = addon.imageUrl,
                    name = addon.name,
                    price = addon.price,
                    calories = addon.nutritionInfo?.calories,
                    quantity = addon.quantity,
                    onQuantityChange = { newQuantity ->
                        onQuantityChange(index, newQuantity)
                    }
                )
            }
        }
    }
}

@AppPreviews
@Composable
private fun AddOnsSectionPreview() {
    FLODRestaurantTheme {
        AddOnsSection(
            modifier = Modifier
                .statusBarsPadding()
                .padding(20.dp),
            addOns = listOf(
                AddonUiModel(
                    id = "1",
                    name = "Extra Cheese",
                    price = 2.5,
                    imageUrl = "https://example.com/cheese.png",
                    nutritionInfo = AddonNutritionInfo(
                        calories = 150,
                        protein = 8.0,
                        carbs = 2.0,
                        fat = 10.0
                    ),
                    quantity = 1
                ),
                AddonUiModel(
                    id = "2",
                    name = "Bacon Bits",
                    price = 3.0,
                    imageUrl = "https://example.com/bacon.png",
                    nutritionInfo = AddonNutritionInfo(
                        calories = 150,
                        protein = 8.0,
                        carbs = 2.0,
                        fat = 10.0
                    ),
                    quantity = 0
                ),
            ),
            onQuantityChange = { _, _ -> }
        )
    }
}
