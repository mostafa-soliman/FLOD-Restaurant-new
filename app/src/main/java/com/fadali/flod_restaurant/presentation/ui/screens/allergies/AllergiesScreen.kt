package com.fadali.flod_restaurant.presentation.ui.screens.allergies

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.ui.platform.LocalContext
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue

data class Allergy(
    val id: String,
    val name: String,
    val emoji: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AllergiesScreen(
    items: List<Allergy>,
    onStart: (selected: List<Allergy>) -> Unit = {}
) {
    val cs = MaterialTheme.colorScheme

    var query by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val setSaver = listSaver<Set<String>, String>(
        save = { it.toList() },
        restore = { it.toSet() }
    )
    var selectedIds by rememberSaveable(stateSaver = setSaver) { mutableStateOf(emptySet()) }

    val filtered = remember(items, query) {
        if (query.isBlank()) items
        else items.filter { it.name.contains(query, ignoreCase = true) }
    }

    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 36.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.allergies_title_line1) + "\n" +
                        stringResource(R.string.allergies_title_line2),
                color = cs.accentBlue,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                placeholder = { Text(text = stringResource(R.string.search_allergens_hint)) },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = cs.onSurface.copy(alpha = .6f),
                        modifier = Modifier.size(18.dp)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFEAEAEA),
                    cursorColor = cs.primary
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(Modifier.height(16.dp))

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                filtered.forEach { item ->
                    val selected = item.id in selectedIds
                    AllergyChip(
                        label = item.name,
                        emoji = item.emoji,
                        selected = selected,
                        onToggle = {
                            selectedIds =
                                if (selected) selectedIds - item.id else selectedIds + item.id
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val selected = items.filter { it.id in selectedIds }
                    onStart(selected)
                },
                enabled = selectedIds.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = cs.primary,
                    contentColor = cs.onPrimary,
                    disabledContainerColor = cs.primary.copy(alpha = .4f),
                    disabledContentColor = cs.onPrimary
                )
            ) {
                Text(
                    text = stringResource(R.string.start_label),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun AllergyChip(
    label: String,
    emoji: String,
    selected: Boolean,
    onToggle: () -> Unit
) {
    val cs = MaterialTheme.colorScheme
    val bg = if (selected) cs.primary else Color.White
    val fg = if (selected) Color.White else Color.Black
    val border = if (selected) cs.primary else Color(0xFFE0E0E0)

    Surface(
        onClick = onToggle,
        shape = RoundedCornerShape(12.dp),
        color = bg,
        contentColor = fg,
        border = BorderStroke(1.dp, border),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, color = fg, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.width(8.dp))
            Text(text = emoji, fontSize = 16.sp, color = fg)
        }
    }
}


/* ---------------- Emoji  ---------------- */
private const val EGG = "\uD83E\uDD5A"
private const val MILK = "\uD83E\uDD5B"
private const val CHICKEN = "\uD83E\uDD57"
private const val CHILI = "\uD83C\uDF36\uFE0F"
private const val MEAT = "\uD83C\uDF56"
private const val WHEAT = "\uD83C\uDF5E"
private const val FISH = "\uD83D\uDC1F"
private const val PEANUT = "\uD83E\uDD5C"
private const val TREE_NUT = "\uD83C\uDF30"
private const val SHELL = "\uD83E\uDD90"
private const val SOY = "\uD83C\uDF31"
private const val MOLD = "\uD83E\uDD32"
private const val PEACH = "\uD83C\uDF51"
private const val CORN = "\uD83C\uDF3D"
private const val APPLE = "\uD83C\uDF4E"
private const val BROCCOLI = "\uD83E\uDD66"
private const val STRAWBERRY = "\uD83C\uDF53"

/* ---------------- Preview بيانات وهمية ---------------- */
private val allAllergensDemo = listOf(
    Allergy("eggs", "Eggs", EGG),
    Allergy("milk", "Lactose", MILK),
    Allergy("chicken", "Chicken", "\uD83C\uDF57"),
    Allergy("spice", "Spice", CHILI),
    Allergy("meat", "Meat", MEAT),
    Allergy("gluten", "Gluten", WHEAT),
    Allergy("fish", "Fish", FISH),
    Allergy("peanuts", "Peanuts", PEANUT),
    Allergy("tree_nuts", "Tree Nuts", TREE_NUT),
    Allergy("shellfish", "Shellfish", SHELL),
    Allergy("soy", "Soy", SOY),
    Allergy("mold", "Mold", MOLD),
    Allergy("peach", "Peach", PEACH),
    Allergy("corn", "Corn", CORN),
    Allergy("apple", "Apple", APPLE),
    Allergy("broccoli", "Broccoli", BROCCOLI),
    Allergy("strawberries", "Strawberries", STRAWBERRY),
)

/* ========================= PREVIEWS ========================= */

@Preview(name = "EN - Light", showSystemUi = true, locale = "en", device = "id:pixel_7_pro")
@Composable
private fun PreviewAllergiesEn() {
    AllergiesScreen(items = allAllergensDemo)
}

@Preview(name = "AR - Light", showSystemUi = true, locale = "ar", device = "id:pixel_7_pro")
@Composable
private fun PreviewAllergiesAr() {
    AllergiesScreen(items = allAllergensDemo)
}

@Preview(
    name = "AR - Dark",
    showSystemUi = true,
    locale = "ar",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "id:pixel_7_pro"
)
@Composable
private fun PreviewAllergiesArDark() {
    AllergiesScreen(items = allAllergensDemo)
}


