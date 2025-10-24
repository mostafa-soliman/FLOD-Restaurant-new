package com.fadali.flod_restaurant.feature.subscription.ui.review.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.card.AppNutrientCard
import com.fadali.flod_restaurant.core.component.card.NutrientType
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.NutritionInfo
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

@Composable
fun NutrientsSection(
    modifier: Modifier = Modifier,
    nutritionInfo: NutritionInfo
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.macros),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.appText,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NutrientType.entries.forEach { nutrientType ->
                val nut = when (nutrientType) {
                    NutrientType.CALORIES -> nutritionInfo.calories
                    NutrientType.PROTEIN -> nutritionInfo.protein
                    NutrientType.CARBS -> nutritionInfo.carbohydrates
                    NutrientType.FAT -> nutritionInfo.fats
                }
                AppNutrientCard(
                    value = nut.toString(),
                    nutrientType = nutrientType
                )
            }
        }
    }

}

@AppPreviews
@Composable
private fun NutrientsSectionPreview() {
    FLODRestaurantTheme {
        NutrientsSection(
            modifier = Modifier
                .statusBarsPadding()
                .padding(20.dp),
            nutritionInfo = NutritionInfo(
                calories = 250,
                protein = 30,
                carbohydrates = 20,
                fats = 10,
                title = "",
                id = ""
            )
        )
    }
}
