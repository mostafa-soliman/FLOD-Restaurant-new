package com.fadali.flod_restaurant.core.component.phonenumberfield

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.textfield.AppTextField
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

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
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            AppTextField(
                modifier = Modifier.requiredWidth(110.dp),
                value = "+966",
                onValueChange = {},
                enabled = false,
                prefix = {
                    Image(
                        modifier = Modifier
                            .width(36.dp)
                            .height(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_saudi_flag),
                        contentDescription = stringResource(R.string.saudi_flag_content_desc)
                    )
                }
            )
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

@Preview(showBackground = true)
@Composable
private fun AppPhoneNumberFieldPreview() {
    FLODRestaurantTheme {
        AppPhoneNumberField(
            modifier = Modifier.statusBarsPadding(),
            phoneNumber = "0123456789",
            onPhoneNumberChanged = {},
            onFocusChanged = {},
            onDone = {},
            validationError = "Invalid phone number"
        )
    }
}
