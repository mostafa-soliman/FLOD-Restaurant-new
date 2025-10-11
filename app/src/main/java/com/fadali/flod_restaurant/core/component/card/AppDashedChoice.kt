package com.fadali.flod_restaurant.core.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun AppDashedChoice(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(6.dp),
    borderWidth: Dp = 1.5.dp,
    dashPattern: FloatArray = floatArrayOf(10f, 10f),
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.outline,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable RowScope.() -> Unit
) {
    val borderColor = if (selected) selectedColor else unselectedColor
    Surface(
        onClick = onClick,
        shape = shape,
        color = backgroundColor,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.dashedBorder(
                width = borderWidth,
                color = borderColor,
                dashPattern = dashPattern
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}
/**
 * Extension function to add dashed border to any Modifier
 */
private fun Modifier.dashedBorder(
    width: Dp,
    color: Color,
    cornerRadius: Dp = 6.dp,
    dashPattern: FloatArray = floatArrayOf(18f, 12f)
): Modifier = this.then(
    Modifier.drawBehind {
        val stroke = Stroke(
            width = width.toPx(),
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(dashPattern)
        )
        // Calculate corner radius from shape - using fixed approach
        val cornerRadius = cornerRadius.toPx() // Adjust as needed for different shapes
        drawRoundRect(
            color = color,
            size = size,
            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
            style = stroke
        )
    }
)
@Preview(showBackground = true, name = "Dashed Choice - Unselected")
@Composable
private fun DashedChoicePreviewUnselected() {
    FLODRestaurantTheme {
        AppDashedChoice(
            selected = false,
            onClick = { }
        ) {
            Text(
                text = "Unselected Choice",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
@Preview(showBackground = true, name = "Dashed Choice - Selected")
@Composable
private fun DashedChoicePreviewSelected() {
    FLODRestaurantTheme {
        AppDashedChoice(
            selected = true,
            onClick = { }
        ) {
            Text(
                text = "Selected Choice",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, name = "Dashed Choice - With Icon")
@Composable
private fun DashedChoicePreviewWithIcon() {
    FLODRestaurantTheme {
        AppDashedChoice(
            selected = true,
            onClick = { }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "12 Days",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Dashed Choice - Custom Style")
@Composable
private fun DashedChoicePreviewCustom() {
    FLODRestaurantTheme {
        AppDashedChoice(
            selected = false,
            onClick = { },
            shape = RoundedCornerShape(20.dp),
            borderWidth = 2.dp,
            dashPattern = floatArrayOf(10f, 5f),
            selectedColor = Color.Green,
            unselectedColor = Color.Red
        ) {
            Text(
                text = "Custom Style",
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true, name = "Dashed Choice - Text Only")
@Composable
private fun DashedChoicePreviewTextOnly() {
    FLODRestaurantTheme {
        AppDashedChoice(
            selected = true,
            onClick = { }
        ) {
            Text(
                text = "x3 Meals",
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, name = "Dashed Choice - Multiple Options")
@Composable
private fun DashedChoicePreviewMultiple() {
    FLODRestaurantTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AppDashedChoice(
                selected = true,
                onClick = { }
            ) {
                Text(
                    text = "12 Days",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            AppDashedChoice(
                selected = false,
                onClick = { }
            ) {
                Text(
                    text = "18 Days",
                    modifier = Modifier.padding(16.dp)
                )
            }
            AppDashedChoice(
                selected = false,
                onClick = { }
            ) {
                Text(
                    text = "28 Days",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
