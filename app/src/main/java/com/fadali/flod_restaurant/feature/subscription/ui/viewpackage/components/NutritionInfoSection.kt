package com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fadali.flod_restaurant.core.component.card.AppNutrientCard
import com.fadali.flod_restaurant.core.component.card.NutrientType

@Composable
fun NutritionInfoSection(
    calories: String,
    protein: String,
    carbs: String,
    fat: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        AppNutrientCard(
            modifier = Modifier.weight(1f),
            value = calories,
            nutrientType = NutrientType.CALORIES
        )
        AppNutrientCard(
            modifier = Modifier.weight(1f),
            value = protein,
            nutrientType = NutrientType.PROTEIN
        )
        AppNutrientCard(
            modifier = Modifier.weight(1f),
            value = carbs,
            nutrientType = NutrientType.CARBS
        )
        AppNutrientCard(
            modifier = Modifier.weight(1f),
            value = fat,
            nutrientType = NutrientType.FAT
        )
    }
}

