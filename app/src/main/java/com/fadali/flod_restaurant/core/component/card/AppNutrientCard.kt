package com.fadali.flod_restaurant.core.component.card

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.nutrientColors

enum class NutrientType(
    val icon: String,
    @StringRes val label: Int
) {
    CALORIES(
        icon = "🔥",
        label = R.string.calories
    ),
    PROTEIN(
        icon = "🥩",
        label = R.string.protein
    ),
    CARBS(
        icon = "🍞",
        label = R.string.carbs
    ),
    FAT(
        icon = "🧈",
        label = R.string.fat
    )
}

@Composable
fun AppNutrientCard(
    modifier: Modifier = Modifier,
    value: String,
    nutrientType: NutrientType,
) {
    val nutrientColors = MaterialTheme.colorScheme.nutrientColors
    val textColor = when (nutrientType) {
        NutrientType.CALORIES -> nutrientColors.calories
        NutrientType.PROTEIN -> nutrientColors.protein
        NutrientType.CARBS -> nutrientColors.carbs
        NutrientType.FAT -> nutrientColors.fat
    }
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0x4ADFDFDF)
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier
                .widthIn(min = 76.dp)
                .heightIn(min = 76.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = nutrientType.icon,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = textColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = stringResource(nutrientType.label),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 12.sp,
                    color = textColor.copy(alpha = 0.8f)
                ),
            )
        }
    }
}

@AppPreviews
@Composable
private fun AllNutrientCardsPreview() {
    FLODRestaurantTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppNutrientCard(
                value = "200",
                nutrientType = NutrientType.CALORIES
            )
            AppNutrientCard(
                value = "150g",
                nutrientType = NutrientType.PROTEIN
            )
            AppNutrientCard(
                value = "30g",
                nutrientType = NutrientType.CARBS
            )
            AppNutrientCard(
                value = "35g",
                nutrientType = NutrientType.FAT
            )
        }
    }
}
