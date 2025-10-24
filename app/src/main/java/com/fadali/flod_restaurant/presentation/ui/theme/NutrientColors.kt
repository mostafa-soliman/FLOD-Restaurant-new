package com.fadali.flod_restaurant.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class NutrientColors(
    val calories: Color,
    val protein: Color,
    val carbs: Color,
    val fat: Color
)

val DefaultNutrientColors = NutrientColors(
    calories = CaloriesColor,
    protein = ProteinColor,
    carbs = CarbColor,
    fat = FatColor
)

val LocalNutrientColors = staticCompositionLocalOf { DefaultNutrientColors }

val androidx.compose.material3.ColorScheme.nutrientColors: NutrientColors
    @Composable
    @ReadOnlyComposable
    get() = LocalNutrientColors.current
