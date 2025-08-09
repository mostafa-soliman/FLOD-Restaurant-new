package com.fadali.flod_restaurant.presentation.ui.screens.verify

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun VerifyOtpScreen(
    maskedPhone: String = "+966xxxxxxxx87",
    digitsCount: Int = 6,
    initialSeconds: Int = 60,
    onCodeComplete: (String) -> Unit = {},
    onResend: () -> Unit = {}
) {
    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val onSurface = MaterialTheme.colorScheme.onSurface
    val surface = MaterialTheme.colorScheme.surface
    val background = MaterialTheme.colorScheme.background
    val secondary = MaterialTheme.colorScheme.secondary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Verify your Number",
            color = primary,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(16.dp))

        // Subtitle
        Text(
            text = "we've sent an SMS to $maskedPhone",
            color = onSurface.copy(alpha = 0.8f),
            fontSize = 14.sp,
            lineHeight = 18.sp
        )

        Spacer(Modifier.height(24.dp))

        // ---- OTP BOXES ----
        val boxes = remember { MutableList(digitsCount) { "" } }
        val focusers = remember { List(digitsCount) { FocusRequester() } }

        Row(
            modifier = Modifier
                .fillMaxWidth(),                // يملى العرض بالكامل
            horizontalArrangement = Arrangement.spacedBy(8.dp) // مسافة ثابتة بين الخانات
        ) {
            repeat(digitsCount) { index ->
                OutlinedTextField(
                    value = boxes[index],
                    onValueChange = { new ->
                        val onlyDigits = new.filter { it.isDigit() }
                        when {
                            onlyDigits.isEmpty() -> boxes[index] = ""
                            onlyDigits.length == 1 -> {
                                boxes[index] = onlyDigits
                                if (index < digitsCount - 1) {
                                    focusers[index + 1].requestFocus()
                                } else {
                                    val code = boxes.joinToString("")
                                    if (code.length == digitsCount) onCodeComplete(code)
                                }
                            }
                            else -> {
                                var cursor = index
                                onlyDigits.forEach { ch ->
                                    if (cursor < digitsCount) {
                                        boxes[cursor] = ch.toString()
                                        cursor++
                                    }
                                }
                                if (cursor <= digitsCount - 1) {
                                    focusers[cursor].requestFocus()
                                } else {
                                    val code = boxes.joinToString("")
                                    if (code.length == digitsCount) onCodeComplete(code)
                                }
                            }
                        }
                    },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 22.sp, textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    modifier = Modifier
                        .weight(1f, fill = true)     // <<< الحل: عرض متساوي لكل خانة
                        .height(56.dp)               // ارتفاع ثابت (زي التصميم)
                        .focusRequester(focusers[index])
                        .onPreviewKeyEvent { event ->
                            if (event.key == Key.Backspace && boxes[index].isEmpty() && index > 0) {
                                focusers[index - 1].requestFocus()
                            }
                            false
                        },
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = surface,
                        unfocusedContainerColor = surface,
                        focusedBorderColor = primary.copy(alpha = 0.5f),
                        unfocusedBorderColor = primary.copy(alpha = 0.2f),
                        cursorColor = primary,
                        focusedTextColor = onSurface,
                        unfocusedTextColor = onSurface
                    )
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // ---- RESEND TIMER ----
        var seconds by remember { mutableStateOf(initialSeconds) }
        LaunchedEffect(seconds) {
            if (seconds > 0) {
                delay(1000)
                seconds -= 1
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Resend in",
                color = onSurface.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            if (seconds > 0) {
                val mm = (seconds / 60).toString().padStart(2, '0')
                val ss = (seconds % 60).toString().padStart(2, '0')
                Text(
                    text = "$mm:$ss",
                    color = secondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                // فعّل إعادة الإرسال
                Text(
                    text = "Resend",
                    color = secondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .then(
                            Modifier
                                .clickable  {
                                    onResend()
                                    seconds = initialSeconds
                                }
                        )
                )
            }
        }
    }
}


