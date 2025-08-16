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
import com.fadali.flod_restaurant.presentation.ui.screens.onboard.OnboardScreenContent
import kotlinx.coroutines.delay


@Composable
fun VerifyOtpScreen(
    maskedPhone: String = "+966xxxxxxxx87",
    digitsCount: Int = 4,                 // زي الصورة
    initialSeconds: Int = 60,
    onCodeComplete: (String) -> Unit = {},
    onResend: () -> Unit = {}
) {
    val primaryRed = Color(0xFF700B18)
    val accentBlue = Color(0xFF00A9B4)
    val grayText   = Color(0xFF9E9E9E)

    val cs = MaterialTheme.colorScheme
    val surface = cs.surface
    val onSurface = cs.onSurface
    val background = cs.background

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 84.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "FLOD",
            color = primaryRed,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Verification Code!",
            color = accentBlue,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "We have sent the verification code to your mobile number $maskedPhone",
            color = grayText,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        val focusers = remember { List(digitsCount) { FocusRequester() } }
        var code by rememberSaveable { mutableStateOf(List(digitsCount) { "" }) }
        fun joined() = code.joinToString("")
        val boxShape = RoundedCornerShape(12.dp)

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
                        focusedContainerColor   = surface,
                        unfocusedContainerColor = surface,
                        unfocusedBorderColor    = Color(0xFFE0E0E0),
                        focusedBorderColor      = primaryRed,
                        cursorColor             = primaryRed,
                        focusedTextColor        = onSurface,
                        unfocusedTextColor      = onSurface
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
                    text = "Don’t receive a code ? ",
                    color = onSurface.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                if (seconds > 0) {
                    Text(
                        text = "Resend",
                        color = primaryRed.copy(alpha = 0.45f),
                        fontSize = 14.sp
                    )
                } else {
                    Text(
                        text = "Resend",
                        color = primaryRed,
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
                color = grayText.copy(alpha = 0.8f),
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
                containerColor = primaryRed,
                contentColor = Color.White,
                disabledContainerColor = primaryRed.copy(alpha = 0.4f),
                disabledContentColor = Color.White.copy(alpha = 0.9f)
            )
        ) {
            Text("Next", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
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
    VerifyOtpScreen()
}

@Preview(
    name = "EN - Light",
    showSystemUi = true,
    locale = "en",
    device = "id:pixel_7_pro"
)
@Composable
fun PreviewOnboardContent_En_Light() {
    VerifyOtpScreen()
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
    VerifyOtpScreen()
}



