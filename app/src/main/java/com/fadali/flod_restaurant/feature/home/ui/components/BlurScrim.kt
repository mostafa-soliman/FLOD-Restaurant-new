package com.fadali.flod_restaurant.feature.home.ui.components

import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import android.graphics.RenderEffect as FrameworkRenderEffect

@Composable
fun BlurScrim(
    modifier: Modifier = Modifier,
    blurRadius: Dp = 24.dp,
    scrimColor: Color = Color.Black.copy(alpha = 0.45f),
    shape: Shape = RoundedCornerShape(6.dp),
    content: @Composable BoxScope.() -> Unit = { }
) {
    val density = LocalDensity.current

    val base = modifier
        .clip(shape)
        .let { clippedModifier ->
            // If the device is running Android 12 (API 31/S) or higher, use RenderEffect for a high-quality blur
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // Convert the blur radius from dp to pixels using the current screen density
                val blurRadiusPx = with(density) { blurRadius.toPx() }
                clippedModifier.graphicsLayer {
                    // Apply a RenderEffect blur with the calculated radius
                    renderEffect = FrameworkRenderEffect
                        .createBlurEffect(blurRadiusPx, blurRadiusPx, Shader.TileMode.CLAMP)
                        .asComposeRenderEffect()
                }
            } else {
                // For older Android versions, use the Compose blur modifier
                clippedModifier.blur(blurRadius)
            }
        }

    Box(
        modifier = base.background(scrimColor),
        content = content
    )
}

@AppPreviews
@Composable
fun SubscriptionBlurScrimPreview() {
    FLODRestaurantTheme {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.Red)
        ) {
            BlurScrim(
                modifier = Modifier.fillMaxSize(),
                blurRadius = 12.dp,
                scrimColor = Color.Black.copy(alpha = 0.4f)
            ) {
                Text(
                    text = "Blur Effect",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }
    }
}

@AppPreviews
@Composable
fun SubscriptionBlurScrimVariationsPreview() {
    FLODRestaurantTheme {
        Box(
            modifier = Modifier
                .size(300.dp, 200.dp)
        ) {
            BlurScrim(
                modifier = Modifier
                    .size(150.dp, 80.dp)
                    .align(Alignment.Center),
                blurRadius = 4.dp,
                scrimColor = Color.Black.copy(alpha = 0.45f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Custom Blur",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

