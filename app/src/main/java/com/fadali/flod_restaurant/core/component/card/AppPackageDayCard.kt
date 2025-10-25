package com.fadali.flod_restaurant.core.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun AppPackageDayCard(
    modifier: Modifier = Modifier,
    day: String,
    month: String,
    isSelected: Boolean = false,
    onDayClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.White
    val textColor = if (isSelected) Color.White else Color.Black
    val borderColor = if (isSelected) Color.Transparent else Color(0xFFB2B2B2)

    Column(
        modifier = modifier
            .size(width = 70.dp, height = 71.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(size = 12.dp))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(size = 12.dp))
            .clickable { onDayClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = day,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = textColor,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = month,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = textColor,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPackageDayCardSelectedPreview() {
    FLODRestaurantTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AppPackageDayCard(
                day = "3 May",
                month = "Sun",
                isSelected = false,
                onDayClick = {}
            )
            AppPackageDayCard(
                day = "3 May",
                month = "Mon",
                isSelected = true,
                onDayClick = {}
            )
            AppPackageDayCard(
                day = "3 May",
                month = "Tue",
                isSelected = false,
                onDayClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPackageDayCardInteractivePreview() {
    FLODRestaurantTheme {
        var selectedIndex by remember { mutableStateOf(1) }
        val days = listOf(
            "3 May" to "Sun",
            "3 May" to "Mon",
            "3 May" to "Tue",
            "3 May" to "Wed",
            "3 May" to "Thu"
        )

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            days.forEachIndexed { index, (day, month) ->
                AppPackageDayCard(
                    day = day,
                    month = month,
                    isSelected = selectedIndex == index,
                    onDayClick = { selectedIndex = index }
                )
            }
        }
    }
}

