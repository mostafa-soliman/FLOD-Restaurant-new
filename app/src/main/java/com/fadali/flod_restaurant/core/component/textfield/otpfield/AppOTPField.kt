package com.fadali.flod_restaurant.core.component.textfield.otpfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

@Composable
fun AppOTPField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onComplete: ((String) -> Unit)? = null,
    errorMessage: String? = null,
    enabled: Boolean = true,
    length: Int = 5,
    autoFocus: Boolean = true
) {
    var otpValue by remember { mutableStateOf("") }
    var isInternalError by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    var textFieldValue by remember {
        mutableStateOf(TextFieldValue("", TextRange(0)))
    }

    LaunchedEffect(otpValue) {
        if (textFieldValue.text != otpValue) {
            textFieldValue = TextFieldValue(otpValue, TextRange(otpValue.length))
        }
        if (otpValue.isNotEmpty() && isInternalError) {
            isInternalError = false
        }
    }

    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = null
                ) {
                    focusRequester.requestFocus()
                    keyboardController?.show()
                }
        ) {
            BasicTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    val filteredText = newValue.text.filter { it.isDigit() }.take(length)

                    textFieldValue = TextFieldValue(filteredText, TextRange(filteredText.length))
                    otpValue = filteredText
                    onValueChange(filteredText)

                    if (filteredText.length == length) {
                        onComplete?.invoke(filteredText)
                    }
                },
                modifier = Modifier
                    .alpha(0f)
                    .size(1.dp)
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                cursorBrush = SolidColor(Color.Transparent),
                enabled = enabled
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(length) { index ->
                    OTPBox(
                        digit = otpValue.getOrNull(index)?.toString() ?: "",
                        isFocused = otpValue.length == index && enabled,
                        isError = errorMessage != null || isInternalError,
                        enabled = enabled
                    )
                }
            }
        }
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
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

    LaunchedEffect(Unit) {
        if (autoFocus) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
private fun OTPBox(
    digit: String,
    isFocused: Boolean,
    isError: Boolean,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val borderColor = when {
        isError -> MaterialTheme.colorScheme.error
        isFocused -> MaterialTheme.colorScheme.primary
        digit.isNotEmpty() -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.outline
    }

    val backgroundColor = when {
        !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
        isError -> MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
        isFocused -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        else -> MaterialTheme.colorScheme.surface
    }

    Box(
        modifier = modifier
            .size(56.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (digit.isNotEmpty()) {
            Text(
                text = digit,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.appText
                    } else {
                        MaterialTheme.colorScheme.outline
                    },
                    textAlign = TextAlign.Center
                )
            )
        } else if (!isFocused) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 15.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(16.dp)
                        .height(2.dp)
                        .background(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(1.dp)
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppOTPFieldPreview() {
    FLODRestaurantTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Basic OTP Field",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            AppOTPField(
                onValueChange = { otp -> },
                onComplete = { completeOtp -> },
                autoFocus = false
            )
            Text(
                text = "Error State",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            AppOTPField(
                onValueChange = {},
                errorMessage = stringResource(R.string.invalid_otp_error),
                autoFocus = false
            )
            Text(
                text = "Disabled State",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            AppOTPField(
                onValueChange = {},
                enabled = false,
                autoFocus = false
            )
            Text(
                text = "With State Helper",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            val otpState = rememberOTPState(
                onComplete = { otp -> }
            )
            AppOTPField(
                onValueChange = otpState.setValue,
                onComplete = { otp -> },
                autoFocus = false
            )
        }
    }
}
