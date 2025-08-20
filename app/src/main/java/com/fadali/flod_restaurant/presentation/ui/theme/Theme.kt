package com.fadali.flod_restaurant.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)

private val LightColorScheme = lightColorScheme(
    primary      = PrimaryRed,
    onPrimary    = AppBackground,
    secondary    = AccentBlue,
    onSecondary  = AppBackground,
    background   = AppBackground,
    onBackground = AppText,
    surface      = AppBackground,
    onSurface    = AppText,
    outline      = GrayText,
    error        = ErrorRed,
    onError      = OnErrorText

)

@Composable
fun FLODRestaurantTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
