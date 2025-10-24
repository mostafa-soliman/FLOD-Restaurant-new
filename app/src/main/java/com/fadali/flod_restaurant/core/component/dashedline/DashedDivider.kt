package com.fadali.flod_restaurant.core.component.dashedline

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DashedDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    thickness: Dp = 2.dp,
    dashLength: Dp = 6.dp,
    gapLength: Dp = 6.dp,
    isVertical: Boolean = true
) {
    Canvas(modifier = modifier) {
        if (isVertical) {
            drawLine(
                color = color,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height),
                strokeWidth = thickness.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(dashLength.toPx(), gapLength.toPx()), 0f
                )
            )
        } else {
            drawLine(
                color = color,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = thickness.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(dashLength.toPx(), gapLength.toPx()), 0f
                )
            )
        }
    }
}


