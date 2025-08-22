package com.fadali.flod_restaurant.presentation.ui.screens.verify

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.i18n.LText
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue
import com.fadali.flod_restaurant.presentation.ui.theme.grayText
import kotlinx.coroutines.delay


@Composable
fun VerifyOtpScreen(
    phone: String,
    maskedPhone: String = maskPhone(phone),
    digitsCount: Int = 4,
    initialSeconds: Int = 60,
    onCodeComplete: (String) -> Unit = {},
    onResend: () -> Unit = {}
) {
    val cs = MaterialTheme.colorScheme


    Surface(color = cs.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background( cs.background)
                .padding(horizontal = 24.dp, vertical = 84.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = LText(R.string.brand),
                color = cs.primary,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = LText(R.string.verification_title),
                color = cs.accentBlue,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = LText(R.string.verification_message, maskedPhone),
                color = cs.grayText,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            val focusers = remember { List(digitsCount) { FocusRequester() } }
            var code by rememberSaveable { mutableStateOf(List(digitsCount) { "" }) }
            fun joined() = code.joinToString("")
            val boxShape = RoundedCornerShape(12.dp)
            LaunchedEffect(Unit) {
                focusers.first().requestFocus()
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(digitsCount) { index ->
                    val value = code[index]
                    OutlinedTextField(
                        value = value,
                        onValueChange = { new ->
                            val digits = new.filter { it.isDigit() }
                            when {
                                digits.isEmpty() -> {
                                    code = code.toMutableList().also { it[index] = "" }
                                }

                                digits.length == 1 -> {
                                    code = code.toMutableList().also { it[index] = digits }
                                    if (index < digitsCount - 1) focusers[index + 1].requestFocus()
                                }

                                else -> {
                                    var cursor = index
                                    val next = code.toMutableList()
                                    digits.forEach { ch ->
                                        if (cursor < digitsCount) {
                                            next[cursor] = ch.toString()
                                            cursor++
                                        }
                                    }
                                    code = next
                                    if (cursor < digitsCount) focusers[cursor].requestFocus()
                                }
                            }
                        },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 22.sp, textAlign = TextAlign.Center),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .heightIn(min = 56.dp, max = 72.dp)
                            .focusRequester(focusers[index])
                            .onPreviewKeyEvent { e ->
                                if (e.key == Key.Backspace && code[index].isEmpty() && index > 0) {
                                    focusers[index - 1].requestFocus()
                                }
                                false
                            },
                        shape = boxShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = cs.surface,
                            unfocusedContainerColor = cs.surface,
                            unfocusedBorderColor = cs.outline,
                            focusedBorderColor = cs.primary,
                            cursorColor = cs.primary,
                            focusedTextColor = cs.onSurface,
                            unfocusedTextColor = cs.onSurface
                        )
                    )
                }
            }

            LaunchedEffect(code) {
                val all = joined()
                if (all.length == digitsCount) onCodeComplete(all)
            }

            Spacer(Modifier.height(16.dp))

            // ---- TIMER + RESEND ----
            var seconds by rememberSaveable { mutableStateOf(initialSeconds) }
            LaunchedEffect(seconds) {
                if (seconds > 0) {
                    delay(1000)
                    seconds -= 1
                }
            }
            val timer = "%02d:%02d".format(seconds / 60, seconds % 60)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        text = LText(R.string.didnt_receive_code),
                        color = cs.onSurface.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    if (seconds > 0) {
                        Text(
                            text = LText(R.string.resend),
                            color = cs.primary.copy(alpha = 0.45f),
                            fontSize = 14.sp
                        )
                    } else {
                        Text(
                            text = LText(R.string.resend),
                            color = cs.primary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable {
                                onResend()
                                seconds = initialSeconds
                            }
                        )
                    }
                }

                Text(
                    text = timer,
                    color = cs.grayText.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.height(24.dp))

            // ---- NEXT BUTTON ----
            val enabled = joined().length == digitsCount
            Button(
                onClick = { onCodeComplete(joined()) },
                enabled = enabled,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = cs.primary,
                    contentColor = cs.onPrimary,
                    disabledContainerColor = cs.primary.copy(alpha = 0.4f),
                    disabledContentColor = cs.onPrimary.copy(alpha = 0.9f)
                )
            ) {
                Text(LText(R.string.next), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

fun maskPhone(phone: String): String {
    return if (phone.length >= 2) {
        val lastTwo = phone.takeLast(2)
        val hidden = "*".repeat(phone.length - 2)
        "$hidden$lastTwo"
    } else {
        phone
    }
}

@Preview(
    name = "AR - Light",
    showSystemUi = true,
    locale = "ar",
    device = "id:pixel_7_pro"
)
@Composable
fun PreviewOnboardContent_Ar_Light() {
    VerifyOtpScreen(
        phone = TODO(),
        maskedPhone = TODO(),
        digitsCount = TODO(),
        initialSeconds = TODO(),
        onCodeComplete = TODO(),
        onResend = TODO()
    )
}

@Preview(
    name = "EN - Light",
    showSystemUi = true,
    locale = "en",
    device = "id:pixel_7_pro"
)
@Composable
fun PreviewOnboardContent_En_Light() {
    VerifyOtpScreen(
        phone = TODO(),
        maskedPhone = TODO(),
        digitsCount = TODO(),
        initialSeconds = TODO(),
        onCodeComplete = TODO(),
        onResend = TODO()
    )
}

@Preview(
    name = "AR - Dark",
    showSystemUi = true,
    locale = "ar",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "id:pixel_7_pro"
)
@Composable
fun PreviewOnboardContent_Ar_Dark() {
    VerifyOtpScreen(
        phone = TODO(),
        maskedPhone = TODO(),
        digitsCount = TODO(),
        initialSeconds = TODO(),
        onCodeComplete = TODO(),
        onResend = TODO()
    )
}



