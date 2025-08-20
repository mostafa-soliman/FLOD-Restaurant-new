package com.fadali.flod_restaurant.presentation.ui.screens.fitstart

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue

@Composable
fun FitStartScreen(
    onLogin: (weightKg: String, heightCm: String, level: ActivityLevel) -> Unit = { _,_,_ -> },
    onSkip: () -> Unit = {}
) {
    val cs = androidx.compose.material3.MaterialTheme.colorScheme


    var weight by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var level  by rememberSaveable { mutableStateOf(ActivityLevel.NotActive) }

    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Fit Start", color = cs.accentBlue, fontSize = 28.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(24.dp))

            SectionLabel("Weight")
            UnitTextField(
                value = weight,
                onChange = { weight = it.filter { ch -> ch.isDigit() || ch == '.' }.take(6) },
                hint = "Enter Your Weight",
                unit = "KG",
                border = cs.outline
            )

            Spacer(Modifier.height(16.dp))

            SectionLabel("Height")
            UnitTextField(
                value = height,
                onChange = { height = it.filter { ch -> ch.isDigit() || ch == '.' }.take(6) },
                hint = "Enter Your Height",
                unit = "CM",
                border = cs.outline
            )

            Spacer(Modifier.height(20.dp))

            // Activity Level
            SectionLabel("Activity Level")
            Spacer(Modifier.height(8.dp))

            ActivityOption(
                title = "Not Active",
                emoji = "—",
                selected = level == ActivityLevel.NotActive,
                onClick = { level = ActivityLevel.NotActive },
                border = cs.outline
            )
            ActivityOption(
                title = "Lightly Active",
                emoji = "\uD83D\uDEB6", // 🚶
                selected = level == ActivityLevel.LightlyActive,
                onClick = { level = ActivityLevel.LightlyActive },
                border = cs.outline
            )
            ActivityOption(
                title = "Moderately Active",
                emoji = "\uD83C\uDFC3", // 🏃
                selected = level == ActivityLevel.ModeratelyActive,
                onClick = { level = ActivityLevel.ModeratelyActive },
                border = cs.outline
            )
            ActivityOption(
                title = "Very Active",
                emoji = "\uD83E\uDD3C", // 🤼/🏋 ( )
                selected = level == ActivityLevel.VeryActive,
                onClick = { level = ActivityLevel.VeryActive },
                border = cs.outline
            )
            ActivityOption(
                title = "Extra Active",
                emoji = "\uD83C\uDFD4", // 🏔
                selected = level == ActivityLevel.ExtraActive,
                onClick = { level = ActivityLevel.ExtraActive },
                border = cs.outline
            )

            Spacer(Modifier.weight(1f))

            // Login (primary)
            Button(
                onClick = { onLogin(weight, height, level) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = cs.primary,
                    contentColor   = cs.onPrimary
                ),
                enabled = weight.isNotBlank() && height.isNotBlank()
            ) {
                Text("Login", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(12.dp))

            // Skip (outline)
            OutlinedButton(
                onClick = onSkip,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.5.dp, cs.primary),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = cs.surface,
                    contentColor   = cs.primary
                )
            ) {
                Text("Skip", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

/* ---------- Reusable pieces ---------- */

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        textAlign = TextAlign.Start
    )
}


@Composable
private fun UnitTextField(
    value: String,
    onChange: (String) -> Unit,
    hint: String,
    unit: String,
    border: Color
) {
    val shape = RoundedCornerShape(16.dp)
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        placeholder = { Text(hint, color = Color(0xFFBDBDBD)) },
        singleLine = true,
        shape = shape,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        trailingIcon = {
            Surface(
                shape = CircleShape,
                color = Color(0xFF4E5BA6),
                contentColor = Color.White
            ) {
                Text(
                    text = unit,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = border,
            unfocusedBorderColor = border,
            cursorColor = Color.Black
        )
    )
}

@Composable
private fun ActivityOption(
    title: String,
    emoji: String,
    selected: Boolean,
    onClick: () -> Unit,
    border: Color
) {
    val shape = RoundedCornerShape(16.dp)
    val borderColor = if (selected) Color(0xFFB8E6EA) else border
    val bg = if (selected) Color(0xFFEFFAFB) else Color.White

    Surface(
        onClick = onClick,
        shape = shape,
        border = BorderStroke(1.dp, borderColor),
        color = bg,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(vertical = 6.dp),
        shadowElevation = 0.dp,
        tonalElevation = 0.dp
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.weight(1f))
            Text(emoji, fontSize = 24.sp, modifier = Modifier.clickable { onClick() })
        }
    }
}

/* ---------- Model ---------- */
enum class ActivityLevel {
    NotActive, LightlyActive, ModeratelyActive, VeryActive, ExtraActive
}

/* ========================= PREVIEWS ========================= */

@Preview(
    name = "AR - Light",
    showSystemUi = true,
    locale = "ar",
    device = "id:pixel_7_pro"
)
@Composable
fun PreviewOnboardContent_Ar_Light() {
    FitStartScreen()
}

@Preview(
    name = "EN - Light",
    showSystemUi = true,
    locale = "en",
    device = "id:pixel_7_pro"
)
@Composable
fun PreviewOnboardContent_En_Light() {
    FitStartScreen()
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
    FitStartScreen()
}
