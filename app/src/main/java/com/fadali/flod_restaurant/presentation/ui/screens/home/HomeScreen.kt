package com.fadali.flod_restaurant.presentation.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fadali.flod_restaurant.presentation.ui.components.AutoSwitcherHero
import com.fadali.flod_restaurant.presentation.ui.components.BottomNavBar
import com.fadali.flod_restaurant.presentation.ui.components.HomeTopBar
import com.fadali.flod_restaurant.presentation.ui.components.MealCardItem
import com.fadali.flod_restaurant.util.Dimens

@Composable
fun HomeScreen() {
    val headerImages = listOf(
        "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=1200",
        "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?w=1200"
    )
    val meals = listOf(
        Triple("https://picsum.photos/500/250?3", "Diet Meal", "Select a plan"),
        Triple("https://picsum.photos/500/250?4", "Avocado Salad", "Order now")
    )

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = { HomeTopBar(userName = "Ahmed", avatarUrl = "https://picsum.photos/100") },
        bottomBar = { BottomNavBar(selected = 0, onSelect = {}) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(
                start = Dimens.ScreenPadding,
                end = Dimens.ScreenPadding,
                top = Dimens.CardSpacing,
                bottom = Dimens.CardSpacing
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.CardSpacing)
        ) {
            item {
                AutoSwitcherHero(
                    images = headerImages,
                    title = "Welcome to FLOD !"
                )
            }
            items(meals) { (url, title, cta) ->
                MealCardItem(
                    imageUrl = url,
                    title = title,
                    ratingText = "⭐ 4.6 (9k)",
                    ctaText = cta,
                    onCtaClick = { /* TODO */ }
                )
            }
        }

    }
}

/* ========================= PREVIEWS ========================= */

@Preview(showSystemUi = true, locale = "en", name = "EN")
@Composable
fun PreviewPreparingEn() {
    HomeScreen()
}

@Preview(showSystemUi = true, locale = "ar", name = "AR")
@Composable
fun PreviewPreparingAr() {
    HomeScreen()
}

@Preview(
    name = "AR - Dark",
    showSystemUi = true,
    locale = "ar",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewPreparingArDark() {
    HomeScreen()
}