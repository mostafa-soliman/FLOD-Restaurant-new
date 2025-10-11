package com.fadali.flod_restaurant.core.component.image

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun SaudiRiyalIcon(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_saudi_riyal),
        contentDescription = "Saudi Riyal",
        modifier = modifier.size(16.dp),
        tint = tint
    )
}
@Preview(showBackground = true)
@Composable
private fun SaudiRiyalIconPreview() {
    FLODRestaurantTheme {
        SaudiRiyalIcon()
    }
}