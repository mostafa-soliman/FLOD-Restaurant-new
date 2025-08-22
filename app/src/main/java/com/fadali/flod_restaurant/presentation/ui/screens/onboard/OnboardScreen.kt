package com.fadali.flod_restaurant.presentation.ui.screens.onboard

import android.app.Activity
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.i18n.LText
import com.fadali.flod_restaurant.i18n.LanguageStore
import com.fadali.flod_restaurant.i18n.LocalAppLocale
import com.fadali.flod_restaurant.i18n.LocaleManager
import com.fadali.flod_restaurant.i18n.LocaleUtils
import com.fadali.flod_restaurant.i18n.rememberLanguageController
import com.fadali.flod_restaurant.presentation.ui.navigation.Route
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue
import com.fadali.flod_restaurant.presentation.ui.theme.grayText
import com.fadali.flod_restaurant.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun OnboardScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val state = viewModel.loginState
    var responseText by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    OnboardScreenContent(
        onLoginClick = { phone ->
            viewModel.login(phone)
        },
        onSignUpClick = { navController.navigate(Route.SignUp) },
        responseMsg = responseText,
        isLoading = isLoading,
        viewModel = viewModel
    )

    when (state) {
        is LoginViewModel.LoginState.Loading -> {
            responseText = ""
            isLoading = true
        }

        is LoginViewModel.LoginState.Success -> {
            isLoading = false

            LaunchedEffect(state) {
                val response = state.data ?: return@LaunchedEffect

                // ✅ show toast with message
                val msg = response.message ?: "Login Success"
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

                // ✅ show full json object
                val gson = com.google.gson.Gson()
                val jsonString = gson.toJson(response)
                Toast.makeText(context, jsonString, Toast.LENGTH_LONG).show()

                delay(2000)
                val mobile = response.data?.mobileNumber ?: ""
                navController.navigate("${Route.VerifyOtp}/$mobile")
            }
        }

        is LoginViewModel.LoginState.Error -> {
            isLoading = false
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }

        else -> {}
    }

}

@Composable
fun OnboardScreenContent(
    onLanguageToggle: () -> Unit = {},
    onLoginClick: (String) -> Unit = {},
    onSignUpClick: () -> Unit = {},
    viewModel: LoginViewModel? = null,
    responseMsg: String = "",
    isLoading: Boolean = false
) {
    val fieldHeight = 56.dp
    var phone by rememberSaveable { mutableStateOf("") }
    val cs = androidx.compose.material3.MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            LanguageIcon()
        }

        Spacer(modifier = Modifier.height(38.dp))

        Text(
            text = "FLOD",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = cs.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = LText(R.string.welcome_back),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = cs.accentBlue
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = LText( R.string.sign_in_description),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = cs.grayText
        )

        Spacer(modifier = Modifier.height(24.dp))

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { /* TODO country pick */ },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(110.dp)
                        .height(fieldHeight),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = cs.surface,
                        contentColor = cs.onSurface
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.saudi_flag),
                        contentDescription = LText(R.string.saudi_flag_desc),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("+966", color = cs.onSurface)
                }

                Spacer(modifier = Modifier.width(8.dp))


                OutlinedTextField(
                    value = phone,
                    onValueChange = { input ->
                        phone = toAsciiDigits(input).take(11)
                    },
                    placeholder = {
                        Text(
                            text = LText(R.string.phone_number),
                            color = cs.grayText
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .weight(1f)
                        .height(fieldHeight),
                    shape = RoundedCornerShape(8.dp),
                    textStyle = TextStyle(textDirection = TextDirection.Ltr),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = cs.surface,
                        focusedContainerColor = cs.surface,
                        unfocusedBorderColor = cs.outline,
                        focusedBorderColor = cs.outline,
                        cursorColor = cs.onSurface
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel?.let { onLoginClick(phone) } },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = cs.primary,
                contentColor = cs.onPrimary
            )
        ) {
            Text(text = LText(R.string.login), color = Color.White)
        }

        if (isLoading) {
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator(color = cs.primary)
        }

        if (responseMsg.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text(text = responseMsg, color = cs.error, modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = LText(R.string.signup_prompt),
                color = cs.grayText,
                modifier = Modifier.alignBy(FirstBaseline)
            )
            Spacer(
                modifier = Modifier
                    .width(4.dp)
                    .alignBy(FirstBaseline)
            )
            TextButton(
                onClick = onSignUpClick,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.alignBy(FirstBaseline)
            ) {
                Text(
                    text = LText(R.string.signup_link),
                    color = cs.primary,
                    textDecoration = TextDecoration.None,
                    modifier = Modifier.alignBy(FirstBaseline)
                )
            }
        }
    }
}

private fun toAsciiDigits(input: String): String = buildString {
    input.forEach { ch ->
        val d = ch.digitToIntOrNull()
        if (d != null) append(('0'.code + d).toChar())
    }
}
@Composable
fun LanguageIcon(
    modifier: Modifier = Modifier.padding(16.dp)
) {
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
    val setLang = rememberLanguageController()
    val current = LocalAppLocale.current
    Surface(
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .clickable {
                val next = if (current.equals("ar", true)) "en" else "ar"
                setLang(next)
//                val next = LocaleUtils.toggled()
//                scope.launch { LanguageStore.save(context, next) }
//                LocaleManager.setAppLanguage(next, true, context as? Activity)

            },
        tonalElevation = 0.dp
    ) {
        Image(
            painter = painterResource(R.drawable.ic_globe),
            contentDescription = "Change Language",
            modifier = Modifier.size(22.dp)
        )
    }
}



@Preview(name = "AR - Light", showSystemUi = true, locale = "ar", device = "id:pixel_7_pro")
@Composable
fun PreviewOnboardContent_Ar_Light() {
    OnboardScreenContent()
}

@Preview(name = "EN - Light", showSystemUi = true, locale = "en", device = "id:pixel_7_pro")
@Composable
fun PreviewOnboardContent_En_Light() {
    OnboardScreenContent()
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
    OnboardScreenContent()
}
