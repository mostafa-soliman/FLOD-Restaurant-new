package com.fadali.flod_restaurant.feature.home.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.core.component.image.AppNetworkImage
import com.fadali.flod_restaurant.core.component.image.AppShimmerBox
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.util.Dimens
import kotlinx.coroutines.delay

@Composable
fun FeatureHero(
    images: List<Any>,
    title: String,
    modifier: Modifier = Modifier,
    intervalMillis: Long = 5_000L
) {
    val shape = RoundedCornerShape(6.dp)
    var index by remember(images) { mutableIntStateOf(0) }

    LaunchedEffect(images) {
        if (images.size > 1) {
            while (true) {
                delay(intervalMillis)
                index = (index + 1) % images.size
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.HeaderHeight)
            .clip(shape)
    ) {
        when {
            images.isEmpty() -> {
                AppShimmerBox(Modifier.matchParentSize())
            }

            images.size == 1 -> {
                AppNetworkImage(
                    model = images.getOrNull(0),
                    modifier = Modifier.fillMaxSize(),
                    shape = null
                )
            }

            else -> {
                AnimatedContent(
                    targetState = index,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    modifier = Modifier.fillMaxSize(),
                    label = "hero_fade_switch"
                ) { i ->
                    AppNetworkImage(
                        model = images.getOrNull(i),
                        modifier = Modifier.fillMaxSize(),
                        shape = null
                    )
                }
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
            ),
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(0.85f)
                .padding(start = 16.dp, top = 16.dp)
        )
    }
}

@AppPreviews
@Composable
private fun SubscriptionFeatureHeroPreview() {
    val sampleImages = listOf(
        "https://images.unsplash.com/photo-1504674900247-0877df9cc836",
        "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445",
        "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe"
    )
    FeatureHero(
        images = sampleImages,
        title = "Delicious Food",
        intervalMillis = 3_000L
    )
}
