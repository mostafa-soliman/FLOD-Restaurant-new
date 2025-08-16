package com.fadali.flod_restaurant.presentation.ui.screens.signup

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.util.UiDimensions

@Composable
fun SignUpScreen(
    onContinue: (name: String, phone: String, email: String, birthday: String, gender: Gender) -> Unit = { _,_,_,_,_ -> }
) {
    val primaryRed = Color(0xFF700B18)
    val accentBlue = Color(0xFF00A9B4)
    val grayText   = Color(0xFF9E9E9E)

    // State
    var email by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var birthday by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf(Gender.Male) }

    val cs = MaterialTheme.colorScheme
    val fieldShape = RoundedCornerShape(16.dp)
    val fieldHeight = UiDimensions.TextFieldHeight

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 84.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("FLOD", color = primaryRed, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text("Sign Up", color = accentBlue, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(6.dp))
        Text("Enter your personal information", color = grayText, fontSize = 14.sp)

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            shape = fieldShape,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFFE0E0E0),
                unfocusedBorderColor = Color(0xFFEAEAEA),
                cursorColor = cs.primary
            )
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = name, onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true,
            shape = fieldShape,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFFE0E0E0),
                unfocusedBorderColor = Color(0xFFEAEAEA),
                cursorColor = cs.primary
            )
        )

        Spacer(Modifier.height(12.dp))

        // Phone row (flag + code + number)
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
                border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.saudi_flag),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("+966", color = cs.onSurface)
            }

            Spacer(Modifier.width(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it.filter(Char::isDigit).take(10) },
                label = { Text("Phone Number") },
                singleLine = true,
                shape = fieldShape,
                modifier = Modifier
                    .weight(1f)
                    .height(fieldHeight),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFEAEAEA),
                    cursorColor = cs.primary
                )
            )
        }

        Spacer(Modifier.height(12.dp))

        // Birthday with trailing icon -> DatePickerDialog
        val context = LocalContext.current
        val cal = remember { java.util.Calendar.getInstance() }
        val datePicker = remember(context) {
            android.app.DatePickerDialog(
                context,
                { _, y, m, d -> birthday = "%d / %d / %d".format(d, m + 1, y) },
                cal.get(java.util.Calendar.YEAR),
                cal.get(java.util.Calendar.MONTH),
                cal.get(java.util.Calendar.DAY_OF_MONTH)
            )
        }

        OutlinedTextField(
            value = birthday,
            onValueChange = { /* read-only */ },
            label = { Text("Birthday") },
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { datePicker.show() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Pick date",
                        tint = cs.onSurface
                    )
                }
            },
            shape = fieldShape,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFFE0E0E0),
                unfocusedBorderColor = Color(0xFFEAEAEA),
                cursorColor = cs.primary
            )
        )

        Spacer(Modifier.height(16.dp))

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Gender",
            color = cs.onBackground.copy(alpha = .85f),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(Modifier.height(8.dp))

//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            GenderPill(
//                imageRes = R.drawable.male,
//                label = "Male",
//                selected = gender == Gender.Male,
//                onClick = { gender = Gender.Male },
//                modifier = Modifier.weight(1f)
//            )
//            GenderPill(
//                imageRes = R.drawable.female,
//                label = "Female",
//                selected = gender == Gender.Female,
//                onClick = { gender = Gender.Female },
//                modifier = Modifier.weight(1f)
//            )
//
//
//        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GenderChoice(
                label = "Male",
                iconRes = R.drawable.male,
                selected = gender == Gender.Male,
                onClick = { gender = Gender.Male },
                accentBlue = accentBlue,
                modifier = Modifier.weight(1f)
            )
            GenderChoice(
                label = "Female",
                iconRes = R.drawable.female,
                selected = gender == Gender.Female,
                onClick = { gender = Gender.Female },
                accentBlue = accentBlue,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))


        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onContinue(name, "+966$phone", email, birthday, gender) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryRed,
                contentColor = Color.White
            ),
            enabled = email.isNotBlank() && name.isNotBlank() && phone.length >= 8 && birthday.isNotBlank()
        ) {
            Text("Next", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
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
    val borderColor = if (selected) accentBlue else Color(0xFFE0E0E0)
    val pillShape   = RoundedCornerShape(12.dp)

    Surface(
        onClick = onClick,
        shape = pillShape,
        color = Color.White,
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


enum class Gender { Male, Female }

@Composable
private fun GenderPill(
    imageRes: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (selected) cs.primary else cs.surface,
        contentColor = if (selected) cs.onPrimary else cs.onSurface,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
        modifier = modifier
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = label,
                modifier = Modifier.size(22.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.width(8.dp))
            Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        }
    }
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
    SignUpScreen()
}

@Preview(
    name = "EN - Light",
    showSystemUi = true,
    locale = "en",
    device = "id:pixel_7_pro"
)
@Composable
fun PreviewOnboardContent_En_Light() {
    SignUpScreen()
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
    SignUpScreen()
}