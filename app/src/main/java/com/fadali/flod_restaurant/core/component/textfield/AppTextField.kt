package com.fadali.flod_restaurant.core.component.textfield

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.appText,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    ),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    label: String? = null,
    maxLines: Int = 1,
    maxLength: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: String? = null,
    errorMessage: String? = null,
    @DrawableRes errorMessageIcon: Int = R.drawable.ic_error_required_icon,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    onDone: (() -> Unit)? = null,
    onNext: (() -> Unit)? = null,
    onPrevious: (() -> Unit)? = null,
    onSearch: (() -> Unit)? = null,
    onSend: (() -> Unit)? = null,
    onGo: (() -> Unit)? = null,
    onFocusChanged: (Boolean) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    val enhancedKeyboardActions = KeyboardActions(
        onDone = { 
            focusManager.clearFocus()
            onDone?.invoke()
        },
        onNext = {
            onNext?.invoke()
            focusManager.moveFocus(FocusDirection.Down)
        },
        onPrevious = {
            onPrevious?.invoke()
            focusManager.moveFocus(FocusDirection.Up)
        },
        onSearch = { 
            focusManager.clearFocus()
            onSearch?.invoke()
        },
        onSend = { 
            focusManager.clearFocus()
            onSend?.invoke()
        },
        onGo = { 
            focusManager.clearFocus()
            onGo?.invoke()
        }
    )

    val enhancedKeyboardOptions = KeyboardOptions(
        imeAction = when {
            onDone != null -> ImeAction.Done
            onNext != null -> ImeAction.Next
            onPrevious != null -> ImeAction.Previous
            onSearch != null -> ImeAction.Search
            onSend != null -> ImeAction.Send
            onGo != null -> ImeAction.Go
            else -> ImeAction.Default
        },
        showKeyboardOnFocus = true,
        autoCorrectEnabled = false,
        keyboardType = keyboardType
    )

    val textFieldShape = RoundedCornerShape(8.dp)

    Column {
        OutlinedTextField(
            modifier = modifier
                .height(
                    if (label == null) 60.dp
                    else if (enabled) 68.dp
                    else 60.dp
                )
                .background(
                    color = backgroundColor,
                    shape = textFieldShape
                )
                .onFocusChanged { focusState ->
                    val newFocusState = focusState.isFocused
                    if (isFocused != newFocusState) {
                        isFocused = newFocusState
                        onFocusChanged(newFocusState)
                    }
                },
            value = value,
            onValueChange = { newValue ->
                val filteredValue = if (maxLength != null && newValue.length > maxLength) {
                    newValue.take(maxLength)
                } else {
                    newValue
                }
                onValueChange(filteredValue)
            },
            label = if (label == null) null else {
                {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.appText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        ),
                    )
                }
            },
            placeholder = if (placeholder.isNullOrEmpty()) null else {
                {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.appText.copy(alpha = 0.4f),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        ),
                    )
                }
            },
            readOnly = readOnly,
            enabled = enabled,
            singleLine = maxLines == 1,
            maxLines = maxLines,
            keyboardOptions = enhancedKeyboardOptions,
            keyboardActions = enhancedKeyboardActions,
            shape = textFieldShape,
            textStyle = textStyle,
            isError = errorMessage != null,
            supportingText = null, // Keep null to maintain consistent height
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = suffix,
            interactionSource = interactionSource,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )
        
        // Supporting text outside the TextField to maintain consistent height
        Row(
            modifier = Modifier
                .height(20.dp)
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (errorMessage != null) {
                Image(
                    modifier = Modifier.padding(start = 5.dp),
                    imageVector = ImageVector.vectorResource(id = errorMessageIcon),
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

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    FLODRestaurantTheme {
        Column {
            AppTextField(
                value = "",
                onValueChange = {},
                keyboardType = KeyboardType.Phone,
                label = stringResource(R.string.phone_number),
                maxLength = 11,
                leadingIcon = {
                    Image(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null
                    )
                }
            )
            AppTextField(
                value = "123@abc.com",
                onValueChange = {},
                keyboardType = KeyboardType.Email,
                maxLength = 50,
                errorMessage = "This field is required",
            )
            AppTextField(
                value = "123@abc.com",
                onValueChange = {},
                keyboardType = KeyboardType.Email,
                label = "Email",
                maxLength = 50,
                errorMessage = "This field is required",
            )
            AppTextField(
                value = "Short text",
                onValueChange = {},
                label = "Limited Input",
                maxLength = 20
            )
        }
    }
}
