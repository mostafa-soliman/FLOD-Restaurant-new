package com.fadali.flod_restaurant.core.component.textfield.otpfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberOTPState(
    length: Int = 5,
    onValueChange: ((String) -> Unit)? = null,
    onComplete: ((String) -> Unit)? = null
): OTPState {
    var value by remember { mutableStateOf("") }
    var isComplete by remember { mutableStateOf(false) }
    var isValid by remember { mutableStateOf(true) }

    LaunchedEffect(value) {
        isComplete = value.length == length
        isValid = value.all { it.isDigit() }
        onValueChange?.invoke(value)
        if (isComplete) {
            onComplete?.invoke(value)
        }
    }

    return remember(value, isComplete, isValid) {
        OTPState(
            value = value,
            isComplete = isComplete,
            isValid = isValid,
            clear = { value = "" },
            setValue = { newValue -> value = newValue.filter { it.isDigit() }.take(length) }
        )
    }
}

data class OTPState(
    val value: String,
    val isComplete: Boolean,
    val isValid: Boolean,
    val clear: () -> Unit,
    val setValue: (String) -> Unit
)
