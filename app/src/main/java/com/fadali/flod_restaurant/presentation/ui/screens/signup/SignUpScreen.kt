package com.fadali.flod_restaurant.presentation.ui.screens.signup

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.i18n.LText
import com.fadali.flod_restaurant.presentation.ui.navigation.Route
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue
import com.fadali.flod_restaurant.presentation.ui.theme.grayText
import com.fadali.flod_restaurant.presentation.viewmodel.SignupViewModel
import com.fadali.flod_restaurant.util.Dimens
import java.util.Calendar

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel(),
    onContinue: (name: String, phone: String, email: String, birthday: String, gender: Gender) -> Unit = { _, _, _, _, _ -> }
) {

    val state = viewModel.state
    val context = LocalContext.current

    // State
    var email by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var birthday by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf(Gender.Male) }

    val cs = MaterialTheme.colorScheme
    val fieldShape = RoundedCornerShape(16.dp)
    val fieldHeight = Dimens.TextFieldHeight
    val signUpSuccessMsg = LText(R.string.sign_up_success)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(cs.background)
            .padding(horizontal = 24.dp, vertical = 84.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            LText(R.string.brand),
            color = cs.primary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        Text(
            LText(R.string.signup_title),
            color = cs.accentBlue,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(6.dp))
        Text(LText(R.string.signup_subtitle), color = cs.grayText, fontSize = 14.sp)

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text(LText(R.string.email_label)) },
            singleLine = true,
            shape = fieldShape,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = cs.surface,
                unfocusedContainerColor = cs.surface,
                focusedBorderColor = cs.outline,
                unfocusedBorderColor = cs.outline,
                cursorColor = cs.primary
            )
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = name, onValueChange = { name = it },
            label = { Text(LText(R.string.name_label)) },
            singleLine = true,
            shape = fieldShape,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = cs.surface,
                unfocusedContainerColor = cs.surface,
                focusedBorderColor = cs.outline,
                unfocusedBorderColor = cs.outline,
                cursorColor = cs.primary
            )
        )

        Spacer(Modifier.height(12.dp))

        // Phone row (flag + code + number)
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { /* TODO: choose country */ },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .width(110.dp)
                        .height(fieldHeight),
                    border = BorderStroke(1.dp, cs.outline),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = cs.surface,
                        contentColor = cs.onSurface
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.saudi_flag),
                        contentDescription = LText(R.string.saudi_flag_desc),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(LText(R.string.saudi_country_code), color = cs.onSurface)
                }

                Spacer(Modifier.width(8.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { input ->
                        phone = toAsciiDigits(input).take(10)
                    },
                    label = { Text(LText(R.string.phone_number)) },
                    singleLine = true,
                    shape = fieldShape,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .weight(1f)
                        .height(fieldHeight),
                    textStyle = androidx.compose.ui.text.TextStyle(textDirection = TextDirection.Ltr),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = cs.surface,
                        unfocusedContainerColor = cs.surface,
                        focusedBorderColor = cs.outline,
                        unfocusedBorderColor = cs.outline,
                        cursorColor = cs.primary
                    )
                )
            }
        }
        Spacer(Modifier.height(12.dp))

        // Birthday with trailing icon -> DatePickerDialog
        val context = LocalContext.current
        val cal = remember { Calendar.getInstance() }
        var dateOpen by remember { mutableStateOf(false) }

        val datePicker = remember(context) {
            DatePickerDialog(
                context,
                { _, y, m, d -> birthday = "%d / %d / %d".format(d, m + 1, y) },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).also { dlg ->
                dlg.setOnDismissListener { dateOpen = false } // [ADDED]
            }
        }

        fun openDate() {
            if (!dateOpen) {
                dateOpen = true
                datePicker.show()
            }
        }
        OutlinedTextField(
            value = birthday,
            onValueChange = { /* read-only */ },
            label = { Text(LText(R.string.birthday_label)) },
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { openDate() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = LText(R.string.pick_date_cd),
                        tint = cs.onSurface
                    )
                }
            },
            shape = fieldShape,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { openDate() }
                .onFocusChanged { if (it.isFocused) openDate() },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = cs.surface,
                unfocusedContainerColor = cs.surface,
                focusedBorderColor = cs.outline,
                unfocusedBorderColor = cs.outline,
                cursorColor = cs.primary
            )
        )

        Spacer(Modifier.height(16.dp))

        Spacer(Modifier.height(16.dp))

        Text(
            text = LText(R.string.gender_label),
            color = cs.onBackground.copy(alpha = .85f),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GenderChoice(
                label = LText(R.string.gender_male),
                iconRes = R.drawable.male,
                selected = gender == Gender.Male,
                onClick = { gender = Gender.Male },
                accentBlue = cs.accentBlue,
                modifier = Modifier.weight(1f)
            )
            GenderChoice(
                label = LText(R.string.gender_female),
                iconRes = R.drawable.female,
                selected = gender == Gender.Female,
                onClick = { gender = Gender.Female },
                accentBlue = cs.accentBlue,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))


        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.signUp(name, email, "+966$phone", birthday, gender)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = state !is SignupViewModel.SignUpState.Loading
                    && email.isNotBlank()
                    && name.isNotBlank()
                    && phone.length >= 8
                    && birthday.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = cs.primary,
                contentColor = cs.onPrimary
            )
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(LText(R.string.next), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                if (state is SignupViewModel.SignUpState.Loading) {
                    CircularProgressIndicator(
                        color = cs.onPrimary,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        when (state) {
            is SignupViewModel.SignUpState.Success -> {
                LaunchedEffect(state) {
                    val response = state.data
                    val msg = response.message ?: signUpSuccessMsg
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

                    // TODO: Show full JSON
                    val gson = com.google.gson.Gson()
                    val jsonString = gson.toJson(response)
                    Toast.makeText(context, jsonString, Toast.LENGTH_LONG).show()

                    val mobile = response.data?.mobileNumber ?: ""
                    navController.navigate("${Route.VerifyOtp}/${mobile}")
                    viewModel.resetState()
                }
            }

            is SignupViewModel.SignUpState.Error -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        (state as SignupViewModel.SignUpState.Error).message,
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.resetState()
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun GenderChoice(
    label: String,
    iconRes: Int,
    selected: Boolean,
    onClick: () -> Unit,
    accentBlue: Color,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme
    val borderColor = if (selected) cs.accentBlue else cs.outline
    val pillShape = RoundedCornerShape(12.dp)

    Surface(
        onClick = onClick,
        shape = pillShape,
        color = cs.surface,
        border = BorderStroke(1.dp, borderColor),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = modifier
            .height(56.dp)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .border(BorderStroke(2.dp, borderColor), shape = RoundedCornerShape(50))
            )
            Text(label, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(18.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

private fun toAsciiDigits(input: String): String =
    buildString {
        input.forEach { ch ->
            val d = ch.digitToIntOrNull()
            if (d != null) append(('0'.code + d).toChar())
        }
    }

enum class Gender { Male, Female }

/* ========================= PREVIEWS ========================= */

@Preview(
    name = "AR - Light",
    showSystemUi = true,
    locale = "ar",
    device = "id:pixel_7_pro"
)
@Composable
fun PreviewOnboardContent_Ar_Light() {
    SignUpScreen(
        navController = TODO(),
        viewModel = TODO(),
        onContinue = TODO()
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
    SignUpScreen(
        navController = TODO(),
        viewModel = TODO(),
        onContinue = TODO()
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
    SignUpScreen(
        navController = TODO(),
        viewModel = TODO(),
        onContinue = TODO()
    )
}