package com.fadali.flod_restaurant.core.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.core.component.image.AppNetworkImage
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun AppCategoryChip(
    imageUrl: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(5.dp)
    val backgroundColor = if (selected) colorScheme.primary else colorScheme.surface
    val stroke = if (selected) BorderStroke(1.dp, colorScheme.primary) else BorderStroke(
        1.dp,
        colorScheme.outline
    )

    Surface(
        onClick = onClick,
        shape = shape,
        color = backgroundColor,
        border = stroke,
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
            .widthIn(min = 80.dp)
            .heightIn(min = 80.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AppNetworkImage(
                model = imageUrl,
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape),
                shape = CircleShape
            )
            Text(
                modifier = Modifier.padding(top = 9.dp),
                text = label,
                style =MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@AppPreviews
@Composable
private fun CategoryChipPreview() {
    FLODRestaurantTheme {
        var quantity by remember { mutableIntStateOf(0) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(20.dp)
        ) {
            AppCategoryChip(
                imageUrl = "https://images.unsplash.com/photo-1560000021-3e4b7b252d0a?w=1200",
                label = "Beverages",
                selected = quantity % 2 == 0,
                onClick = { quantity++ }
            )
        }
    }
}