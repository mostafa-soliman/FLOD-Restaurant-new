package com.fadali.flod_restaurant.core.component.multiselect

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.core.network.appconfig.AddressType
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun SegmentedButton(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector? = null,
    selected: Boolean,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    unselectedBackgroundColor: Color = Color.Transparent,
    selectedContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    unselectedContentColor: Color = MaterialTheme.colorScheme.onSurface,
    showBorder: Boolean = false,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(6.dp)
    val backgroundColor = if (selected) selectedBackgroundColor else unselectedBackgroundColor
    val contentColor = if (selected) selectedContentColor else unselectedContentColor
    val border = if (!selected && showBorder) BorderStroke(1.dp, borderColor) else null
    Surface(
        onClick = onClick,
        shape = shape,
        color = backgroundColor,
        border = border,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 7.dp
            ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let { iconVector ->
                    Icon(
                        imageVector = iconVector,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = contentColor
                    ),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SegmentedTwoButtonsPackage(
    modifier: Modifier = Modifier,
    leftLabel: String,
    rightLabel: String,
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = null,
    selectedLeft: Boolean,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    unselectedBackgroundColor: Color = Color.Transparent,
    selectedContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    unselectedContentColor: Color = MaterialTheme.colorScheme.onSurface,
    showBorderOnUnselected: Boolean = true,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    onSelect: (PackageType) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        SegmentedButton(
            label = leftLabel,
            icon = leftIcon,
            selected = selectedLeft,
            selectedBackgroundColor = selectedBackgroundColor,
            unselectedBackgroundColor = unselectedBackgroundColor,
            selectedContentColor = selectedContentColor,
            unselectedContentColor = unselectedContentColor,
            showBorder = !selectedLeft && showBorderOnUnselected,
            borderColor = borderColor,
            onClick = { onSelect(PackageType.PICKUP) }
        )
        SegmentedButton(
            label = rightLabel,
            icon = rightIcon,
            selected = !selectedLeft,
            selectedBackgroundColor = selectedBackgroundColor,
            unselectedBackgroundColor = unselectedBackgroundColor,
            selectedContentColor = selectedContentColor,
            unselectedContentColor = unselectedContentColor,
            showBorder = selectedLeft && showBorderOnUnselected,
            borderColor = borderColor,
            onClick = { onSelect(PackageType.DELIVERY) }
        )
    }
}

@Composable
fun SegmentedTwoButtonsAddress(
    modifier: Modifier = Modifier,
    leftLabel: String,
    rightLabel: String,
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = null,
    selectedLeft: Boolean,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    unselectedBackgroundColor: Color = Color.Transparent,
    selectedContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    unselectedContentColor: Color = MaterialTheme.colorScheme.onSurface,
    showBorderOnUnselected: Boolean = true,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    onSelect: (AddressType) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        SegmentedButton(
            label = leftLabel,
            icon = leftIcon,
            selected = selectedLeft,
            selectedBackgroundColor = selectedBackgroundColor,
            unselectedBackgroundColor = unselectedBackgroundColor,
            selectedContentColor = selectedContentColor,
            unselectedContentColor = unselectedContentColor,
            showBorder = !selectedLeft && showBorderOnUnselected,
            borderColor = borderColor,
            onClick = { onSelect(AddressType.HOME) }
        )
        SegmentedButton(
            label = rightLabel,
            icon = rightIcon,
            selected = !selectedLeft,
            selectedBackgroundColor = selectedBackgroundColor,
            unselectedBackgroundColor = unselectedBackgroundColor,
            selectedContentColor = selectedContentColor,
            unselectedContentColor = unselectedContentColor,
            showBorder = selectedLeft && showBorderOnUnselected,
            borderColor = borderColor,
            onClick = { onSelect(AddressType.WORK) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SegmentedTwoButtonsPreview() {
    var selectedLeft by remember { mutableStateOf(true) }
    FLODRestaurantTheme {
        SegmentedTwoButtonsPackage(
            leftLabel = "Left",
            rightLabel = "Right",
            selectedLeft = selectedLeft,
            onSelect = { type: PackageType -> },
        )
    }
}
