package com.fadali.flod_restaurant.presentation.ui.screens.fitstart

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue
import com.fadali.flod_restaurant.R

@Composable
fun FitStartScreen(
    onLogin: (weightKg: String, heightCm: String, level: ActivityLevel) -> Unit = { _, _, _ -> },
    onSkip: () -> Unit = {}
) {
    val cs = MaterialTheme.colorScheme
    val scroll = rememberScrollState()

    var weight by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }

    // Activity Level: single-select
    var level by rememberSaveable { mutableStateOf(ActivityLevel.NotActive) }

    // Goals: multi-select (toggle)
    val goalsSaver = listSaver<Set<Goal>, String>(
        save = { it.map(Goal::name) },
        restore = { it.map(Goal::valueOf).toSet() }
    )
    var selectedGoals by rememberSaveable(stateSaver = goalsSaver) { mutableStateOf(emptySet<Goal>()) }

    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scroll)
                .padding(horizontal = 24.dp, vertical = 36.dp)
                .navigationBarsPadding()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.fit_start_title),
                color = cs.accentBlue,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(24.dp))

            SectionLabel(stringResource(R.string.weight_label))
            UnitTextField(
                value = weight,
                onChange = { weight = it.filter { ch -> ch.isDigit() || ch == '.' }.take(6) },
                hint = stringResource(R.string.enter_weight_hint),
                unit = stringResource(R.string.kg_unit),
                border = cs.outline
            )

            Spacer(Modifier.height(16.dp))

            SectionLabel(stringResource(R.string.height_label))
            UnitTextField(
                value = height,
                onChange = { height = it.filter { ch -> ch.isDigit() || ch == '.' }.take(6) },
                hint = stringResource(R.string.enter_height_hint),
                unit = stringResource(R.string.cm_unit),
                border = cs.outline
            )

            Spacer(Modifier.height(20.dp))

            // ---------- Activity Level (single) ----------
            SectionLabel(stringResource(R.string.activity_level_label))
            Spacer(Modifier.height(8.dp))

            ActivityOption(
                title = stringResource(R.string.activity_not_active),
                emoji = EMOJI_MINUS,
                selected = level == ActivityLevel.NotActive,
                onClick = { level = ActivityLevel.NotActive },
                border = cs.outline
            )
            ActivityOption(
                title = stringResource(R.string.activity_lightly_active),
                emoji = EMOJI_WALK,
                selected = level == ActivityLevel.LightlyActive,
                onClick = { level = ActivityLevel.LightlyActive },
                border = cs.outline
            )
            ActivityOption(
                title = stringResource(R.string.activity_moderately_active),
                emoji = EMOJI_RUN,
                selected = level == ActivityLevel.ModeratelyActive,
                onClick = { level = ActivityLevel.ModeratelyActive },
                border = cs.outline
            )
            ActivityOption(
                title = stringResource(R.string.activity_very_active),
                emoji = EMOJI_LIFT,
                selected = level == ActivityLevel.VeryActive,
                onClick = { level = ActivityLevel.VeryActive },
                border = cs.outline
            )
            ActivityOption(
                title = stringResource(R.string.activity_extra_active),
                emoji = EMOJI_MOUNTAIN,
                selected = level == ActivityLevel.ExtraActive,
                onClick = { level = ActivityLevel.ExtraActive },
                border = cs.outline
            )

            Spacer(Modifier.height(20.dp))

            // ---------- Goals (multi toggle) ----------
            SectionLabel(stringResource(R.string.goals_label))


            GoalOption(
                title = stringResource(R.string.goal_lose_weight),
                emoji = EMOJI_BICEPS,
                selected = Goal.LoseWeight in selectedGoals,
                onClick = { selectedGoals = selectedGoals.toggle(Goal.LoseWeight) },
                border = cs.outline
            )
            GoalOption(
                title = stringResource(R.string.goal_maintain_weight),
                emoji = EMOJI_BICEPS,
                selected = Goal.MaintainWeight in selectedGoals,
                onClick = { selectedGoals = selectedGoals.toggle(Goal.MaintainWeight) },
                border = cs.outline
            )
            GoalOption(
                title = stringResource(R.string.goal_gain_weight),
                emoji = EMOJI_BICEPS,
                selected = Goal.GainWeight in selectedGoals,
                onClick = { selectedGoals = selectedGoals.toggle(Goal.GainWeight) },
                border = cs.outline
            )
            GoalOption(
                title = stringResource(R.string.goal_gain_muscle),
                emoji = EMOJI_BICEPS,
                selected = Goal.GainMuscle in selectedGoals,
                onClick = { selectedGoals = selectedGoals.toggle(Goal.GainMuscle) },
                border = cs.outline
            )

            Spacer(Modifier.height(24.dp))

            // Continue (primary)
            Button(
                onClick = { onLogin(weight, height, level) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = cs.primary,
                    contentColor = cs.onPrimary
                ),
                enabled = weight.isNotBlank() && height.isNotBlank()
            ) {
                Text(stringResource(R.string.continue_label), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
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
                    contentColor = cs.primary
                )
            ) {
                Text(stringResource(R.string.skip_label), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
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
            Surface(shape = CircleShape, color = Color(0xFF4E5BA6), contentColor = Color.White) {
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
    val cs = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(12.dp)
    val bg = if (selected) cs.primary else Color.White
    val txt = if (selected) cs.onPrimary else Color.Black
    val stroke = if (selected) cs.primary else border

    Surface(
        onClick = onClick,
        shape = shape,
        border = BorderStroke(1.dp, stroke),
        color = bg,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
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
            Text(title, color = txt, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.weight(1f))
            Text(emoji, fontSize = 22.sp, color = txt, modifier = Modifier.clickable { onClick() })
        }
    }
}

@Composable
private fun GoalOption(
    title: String,
    emoji: String,
    selected: Boolean,
    onClick: () -> Unit,
    border: Color
) {
    ActivityOption(
        title = title,
        emoji = emoji,
        selected = selected,
        onClick = onClick,
        border = border
    )
}

/* ---------- Emoji constants ---------- */
private const val EMOJI_MINUS = "—"
private const val EMOJI_WALK = "\uD83D\uDEB6\uFE0F"     // 🚶 + VS16
private const val EMOJI_RUN = "\uD83C\uDFC3\uFE0F"     // 🏃 + VS16
private const val EMOJI_LIFT = "\uD83C\uDFCB\uFE0F"     // 🏋️ + VS16
private const val EMOJI_MOUNTAIN = "\uD83C\uDFD4\uFE0F"     // 🏔️ + VS16
private const val EMOJI_BICEPS = "\uD83D\uDCAA"           // 💪

/* ---------- Models ---------- */
enum class ActivityLevel { NotActive, LightlyActive, ModeratelyActive, VeryActive, ExtraActive }
enum class Goal { LoseWeight, MaintainWeight, GainWeight, GainMuscle }

/* ---------- Helpers ---------- */
private fun <T> Set<T>.toggle(item: T): Set<T> = if (item in this) this - item else this + item

/* ========================= PREVIEWS ========================= */
@Preview(name = "AR - Light", showSystemUi = true, locale = "ar", device = "id:pixel_7_pro")
@Composable
fun PreviewOnboardContent_Ar_Light() {
    FitStartScreen()
}

@Preview(name = "EN - Light", showSystemUi = true, locale = "en", device = "id:pixel_7_pro")
@Composable
fun PreviewOnboardContent_En_Light() {
    FitStartScreen()
}

@Preview(
    name = "AR - Dark", showSystemUi = true, locale = "ar",
    uiMode = Configuration.UI_MODE_NIGHT_YES, device = "id:pixel_7_pro"
)
@Composable
fun PreviewOnboardContent_Ar_Dark() {
    FitStartScreen()
}
