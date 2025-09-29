package com.fadali.flod_restaurant.core.component.multiselect

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.feature.auth.domain.model.Gender
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun AppGenderSelector(
    modifier: Modifier = Modifier,
    selectedGender: Gender,
    onGenderSelected: (Gender) -> Unit,
    errorMessage: String? = null,
    @DrawableRes errorIcon: Int = R.drawable.ic_error_required_icon,
) {
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GenderChoice(
                label = stringResource(R.string.gender_male),
                iconRes = R.drawable.ic_gender_male,
                selected = selectedGender == Gender.Male,
                onClick = { onGenderSelected(Gender.Male) },
            )
            Spacer(modifier = Modifier.size(16.dp))
            GenderChoice(
                label = stringResource(R.string.gender_female),
                iconRes = R.drawable.ic_gender_female,
                selected = selectedGender == Gender.Female,
                onClick = { onGenderSelected(Gender.Female) },
            )
        }

        Row(
            modifier = Modifier
                .height(20.dp)
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (errorMessage != null) {
                Image(
                    modifier = Modifier.padding(start = 5.dp),
                    imageVector = ImageVector.vectorResource(id = errorIcon),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp),
                    text = errorMessage,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Composable
private fun GenderChoice(
    modifier: Modifier = Modifier,
    label: String,
    iconRes: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val borderColor = if (selected) colorScheme.primary else colorScheme.outline

    Box(
        modifier = modifier
            .height(60.dp)
            .background(colorScheme.surface, RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(8.dp))
            .clickable { onClick() },
    ) {
        Row(
            Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = selected,
                onClick = null, // Handled by Box's onClick
                colors = RadioButtonDefaults.colors(
                    selectedColor = colorScheme.primary,
                    unselectedColor = colorScheme.outline
                )
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorScheme.onSurface
                )
            )
            Spacer(Modifier.width(12.dp))
            Image(
                imageVector = ImageVector.vectorResource(id = iconRes),
                contentDescription = label,
                contentScale = ContentScale.Inside
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppGenderSelectorPreview() {
    FLODRestaurantTheme {
        AppGenderSelector(
            selectedGender = Gender.Male,
            onGenderSelected = {},
            errorMessage = "Gender is required"
        )
    }
}
