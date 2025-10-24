package com.fadali.flod_restaurant.core.component.phonenumberfield

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.textfield.AppTextField
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

@Composable
fun AppPhoneNumberField(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit = {},
    onNext: (() -> Unit)? = null,
    onDone: (() -> Unit)? = null,
    validationError: String? = null,
    @DrawableRes errorIcon: Int = R.drawable.ic_error_required_icon,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .heightIn(60.dp)
                        .border(
                            border = BorderStroke(
                                width = 0.8.dp,
                                color = MaterialTheme.colorScheme.outline
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(
                            horizontal = 14.dp,
                            vertical = 18.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .width(36.dp)
                            .height(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_saudi_flag),
                        contentDescription = stringResource(R.string.saudi_flag_content_desc)
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 4.dp),
                        text = "+966",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.appText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                // To match the height of the supporting text of the AppTextField
                Column(
                    modifier = Modifier
                        .heightIn(min = 20.dp)
                        .padding(top = 4.dp)
                        .alpha(0f),
                ) {
                    validationError?.let {
                        Text(
                            text = "A",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = phoneNumber,
                label = stringResource(R.string.phone_number),
                onValueChange = onPhoneNumberChanged,
                keyboardType = KeyboardType.Phone,
                onNext = onNext,
                onDone = onDone,
                maxLength = 10,
                onFocusChanged = onFocusChanged,
                errorMessage = validationError,
                errorMessageIcon = errorIcon
            )
        }
    }
}

@AppPreviews
@Composable
private fun AppPhoneNumberFieldPreview() {
    FLODRestaurantTheme {
        AppPhoneNumberField(
            modifier = Modifier.statusBarsPadding(),
            phoneNumber = "0123456789",
            onPhoneNumberChanged = {},
            onFocusChanged = {},
            onDone = {},
//            validationError = "Invalid phone number"
        )
    }
}
