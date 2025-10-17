package com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun TitleText(
    text: CharSequence,
    modifier: Modifier = Modifier
) {
    Text(
        text = text.toString(),
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ),
        modifier = modifier
            .padding(top = 4.dp)
    )
}

@AppPreviews
@Composable
fun TitleTextPreview() {
    FLODRestaurantTheme {
        TitleText(
            text = "Sample Title"
        )
    }
}
