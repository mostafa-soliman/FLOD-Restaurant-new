package com.fadali.flod_restaurant.core.component.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Preview annotation for English locale with system UI and Pixel 7 Pro device
 */
@Preview(
    name = "English - Light",
    showSystemUi = true, 
    locale = "en", 
    device = "id:pixel_7_pro"
)
annotation class EnglishLightPreview

/**
 * Preview annotation for Arabic locale with system UI and Pixel 7 Pro device
 */
@Preview(
    name = "Arabic - Light",
    showSystemUi = true, 
    locale = "ar", 
    device = "id:pixel_7_pro"
)
annotation class ArabicLightPreview

/**
 * Preview annotation for English locale with dark mode, system UI and Pixel 7 Pro device
 */
@Preview(
    name = "English - Dark",
    showSystemUi = true, 
    locale = "en", 
    device = "id:pixel_7_pro", 
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class EnglishDarkPreview

/**
 * Preview annotation for Arabic locale with dark mode, system UI and Pixel 7 Pro device
 */
@Preview(
    name = "Arabic - Dark",
    showSystemUi = true, 
    locale = "ar", 
    device = "id:pixel_7_pro", 
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class ArabicDarkPreview

/**
 * Preview annotation for small device testing (Small Phone) with English locale
 */
@Preview(
    name = "Small Device - Light",
    showSystemUi = true, 
    locale = "en", 
    device = "id:small_phone"
)
annotation class SmallDevicePreview

/**
 * Preview annotation for large device/tablet testing (Pixel C) with English locale
 */
@Preview(
    name = "Large Device - Light",
    showSystemUi = true, 
    locale = "en", 
    device = "id:pixel_c"
)
annotation class LargeDevicePreview

/**
 * Multi-preview annotation that includes different device sizes
 */
@SmallDevicePreview
@LargeDevicePreview
annotation class DeviceSizePreviews

/**
 * Multi-preview annotation that combines all common preview configurations
 */
@EnglishLightPreview
@ArabicLightPreview
@EnglishDarkPreview
@ArabicDarkPreview
@DeviceSizePreviews
annotation class AppPreviews
