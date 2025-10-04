package com.fadali.flod_restaurant.core.component.multiselect.vertical

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.core.component.textfield.AppTextField
import com.fadali.flod_restaurant.presentation.ui.components.NetworkImage
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

@Composable
fun AppVerticalMultiSelector(
    modifier: Modifier = Modifier,
    title: String,
    options: List<VerticalMultiSelectorItem> = emptyList(),
    onItemSelected: (VerticalMultiSelectorItem) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val textStyle = MaterialTheme.typography.bodyMedium.copy(
        color = colorScheme.appText,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.appText
            ),
        )
        Spacer(modifier = Modifier.size(12.dp))
        options.forEach {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemSelected(it) },
                value = it.title,
                backgroundColor = if (it.isSelected) colorScheme.primary else colorScheme.background,
                enabled = false,
                suffix = {
                    NetworkImage(
                        modifier = Modifier.size(28.dp),
                        url = it.imageUrl,
                        contentScale = ContentScale.Inside
                    )
                },
                textStyle = if (it.isSelected) textStyle.copy(color = colorScheme.onPrimary)
                else textStyle.copy(color = colorScheme.appText)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppVerticalMultiSelectorPreview() {
    FLODRestaurantTheme {
        AppVerticalMultiSelector(
            modifier = Modifier.padding(20.dp),
            title = "Activity Level",
            options = listOf(
                VerticalMultiSelectorItem(
                    id = "1",
                    title = "Not Active",
                    imageUrl = "${BuildConfig.RESOURCES_BASE_URL}Activity/d1d46280-6b5b-4eea-ac7d-c2072155d612/1758467297316_1.png",
                    isSelected = true
                ),
                VerticalMultiSelectorItem(
                    id = "2",
                    title = "Very Active",
                    imageUrl = "${BuildConfig.RESOURCES_BASE_URL}Activity/ea5a8883-31eb-4023-8e6c-a20a93dd384e/1758964466097_1.png",
                    isSelected = false
                ),
            ),
            onItemSelected = {}
        )
    }
}
