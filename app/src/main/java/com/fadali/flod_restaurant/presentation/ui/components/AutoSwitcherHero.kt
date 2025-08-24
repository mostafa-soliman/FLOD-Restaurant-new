package com.fadali.flod_restaurant.presentation.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.util.Dimens
import kotlinx.coroutines.delay

@Composable
fun AutoSwitcherHero(
    images: List<String>,
    title: String,
    modifier: Modifier = Modifier,
    intervalMillis: Long = 5_000L
) {
    val shape = RoundedCornerShape(Dimens.CardRadius)

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
                ShimmerBox(Modifier.matchParentSize())
            }

            images.size == 1 -> {
                NetworkImage(
                    url = images[0],
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
                    NetworkImage(
                        url = images[i],
                        modifier = Modifier.fillMaxSize(),
                        shape = null
                    )
                }
            }
        }

        Text(
            text = title,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
