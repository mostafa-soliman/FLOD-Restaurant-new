package com.fadali.flod_restaurant.presentation.ui.components

import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
import android.graphics.RenderEffect as FrameworkRenderEffect

@Composable
fun BlurScrim(
    modifier: Modifier = Modifier,
    blurRadius: Dp = 14.dp,
    scrimColor: Color = Color.Black.copy(alpha = 0.35f),
    shape: Shape = RoundedCornerShape(16.dp),
    content: @Composable BoxScope.() -> Unit = { }
) {
    val density = LocalDensity.current

    val base = modifier.clip(shape).let { m ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val r = with(density) { blurRadius.toPx() }
            m.graphicsLayer {
                renderEffect = FrameworkRenderEffect
                    .createBlurEffect(r, r, Shader.TileMode.CLAMP)
                    .asComposeRenderEffect()
            }
        } else {
            m.blur(blurRadius)
        }
    }

    Box(
        modifier = base.background(scrimColor),
        content = content
    )
}
