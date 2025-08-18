package com.fadali.flod_restaurant.presentation.ui.screens.onboard

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.presentation.ui.navigation.Route
import com.fadali.flod_restaurant.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val primaryRed = Color(0xFF700B18)
val accentBlue = Color(0xFF00A9B4)
val grayText = Color(0xFF9E9E9E)

@Composable
fun OnboardScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val state = viewModel.loginState
    var responseText by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

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
            responseText =
                "SUCCESS : userId=${state.data?.userId} | userName=${state.data?.userName}"

            LaunchedEffect(state) {
                if (state is LoginViewModel.LoginState.Success) {
                    delay(2000)
                    navController.navigate(Route.VerifyOtp)
                }
            }

        }

        is LoginViewModel.LoginState.Error -> {
            isLoading = false
            responseText = "ERROR : ${state.message}"
        }

        else -> {
            isLoading = false
        }
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
            LanguageIcon { onLanguageToggle() }
        }

        Spacer(modifier = Modifier.height(38.dp))

        Text(
            text = "FLOD",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = primaryRed
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.welcome_back),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = accentBlue
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.sign_in_description),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = grayText
        )

        Spacer(modifier = Modifier.height(24.dp))

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
                    containerColor = Color.White
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.saudi_flag),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("+966", color = Color.Black)
            }

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { input ->
                    phone = input.filter { it.isDigit() }.take(11)
                },
                placeholder = { Text(text = stringResource(id = R.string.phone_number)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .weight(1f)
                    .height(fieldHeight),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel?.let { onLoginClick(phone) } },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryRed)
        ) {
            Text(text = stringResource(id = R.string.login), color = Color.White)
        }

        if (isLoading) {
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        if (responseMsg.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text(text = responseMsg, color = Color.Red, modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.signup_prompt),
                color = grayText,
                modifier = Modifier.alignBy(FirstBaseline)
            )
            Spacer(modifier = Modifier
                .width(4.dp)
                .alignBy(FirstBaseline))
            TextButton(
                onClick = onSignUpClick,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.alignBy(FirstBaseline)
            ) {
                Text(
                    text = stringResource(id = R.string.signup_link),
                    color = primaryRed,
                    textDecoration = TextDecoration.None,
                    modifier = Modifier.alignBy(FirstBaseline)
                )
            }
        }
    }
}

@Composable
private fun LanguageIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        modifier = modifier
            .padding(top = 38.dp)
            .size(44.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_globe),
                contentDescription = "Change Language",
                modifier = Modifier.size(22.dp)
            )
        }
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
