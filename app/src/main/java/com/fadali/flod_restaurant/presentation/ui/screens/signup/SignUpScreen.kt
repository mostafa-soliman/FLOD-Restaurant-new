package com.fadali.flod_restaurant.presentation.ui.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.util.UiDimensions

@Composable
fun SignUpScreen(
    onContinue: (name: String, phone: String, email: String, birthday: String, gender: Gender) -> Unit = { _,_,_,_,_ -> }
) {
    // State
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.Female) }

    val cs = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Sign up",
            color = cs.primary,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        Spacer(Modifier.height(20.dp))

        // Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Name") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = cs.surface,
                unfocusedContainerColor = cs.surface,
                focusedBorderColor = cs.primary.copy(alpha = .25f),
                unfocusedBorderColor = cs.primary.copy(alpha = .15f),
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
                    .height(UiDimensions.TextFieldHeight),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = cs.surface)
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
                onValueChange = { phone = it.filter { ch -> ch.isDigit() }.take(10) },
                placeholder = { Text("Phone Number") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(UiDimensions.TextFieldHeight),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = cs.surface,
                    unfocusedContainerColor = cs.surface,
                    focusedBorderColor = cs.primary.copy(alpha = .25f),
                    unfocusedBorderColor = cs.primary.copy(alpha = .15f),
                    cursorColor = cs.primary
                )
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = "*We will send you a verification code",
            color = cs.secondary.copy(alpha = .8f),
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(Modifier.height(12.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = cs.surface,
                unfocusedContainerColor = cs.surface,
                focusedBorderColor = cs.primary.copy(alpha = .25f),
                unfocusedBorderColor = cs.primary.copy(alpha = .15f),
                cursorColor = cs.primary
            )
        )

        Spacer(Modifier.height(12.dp))

        // Birthday with trailing icon -> DatePickerDialog
        val context = LocalContext.current
        val cal = remember { java.util.Calendar.getInstance() }

        val datePicker = remember(context) {
            android.app.DatePickerDialog(
                context,
                { _, y, m, d -> birthday = "%02d/%02d/%04d".format(d, m + 1, y) },
                cal.get(java.util.Calendar.YEAR),
                cal.get(java.util.Calendar.MONTH),
                cal.get(java.util.Calendar.DAY_OF_MONTH)
            )
        }


        OutlinedTextField(
            value = birthday,
            onValueChange = { /* read-only via picker */ },
            placeholder = { Text("Birthday") },
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
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = cs.surface,
                unfocusedContainerColor = cs.surface,
                focusedBorderColor = cs.primary.copy(alpha = .25f),
                unfocusedBorderColor = cs.primary.copy(alpha = .15f),
                cursorColor = cs.primary
            )
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Gender",
            color = cs.onBackground.copy(alpha = .85f),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GenderPill(
                imageRes = R.drawable.male,
                label = "Male",
                selected = gender == Gender.Male,
                onClick = { gender = Gender.Male },
                modifier = Modifier.weight(1f)
            )
            GenderPill(
                imageRes = R.drawable.female,
                label = "Female",
                selected = gender == Gender.Female,
                onClick = { gender = Gender.Female },
                modifier = Modifier.weight(1f)
            )


        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onContinue(name, "+966$phone", email, birthday, gender) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = cs.primary,
                contentColor = cs.onPrimary
            ),
            enabled = name.isNotBlank() && phone.length >= 8
        ) {
            Text("Continue", fontSize = 16.sp)
        }
    }
}

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



enum class Gender { Male, Female }
