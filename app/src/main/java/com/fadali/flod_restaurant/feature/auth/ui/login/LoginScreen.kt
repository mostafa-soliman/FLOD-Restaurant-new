package com.fadali.flod_restaurant.feature.auth.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.modifiers.clearFocusOnTap
import com.fadali.flod_restaurant.core.component.phonenumberfield.AppPhoneNumberField
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.feature.auth.ui.otp.OtpFlowType
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue
import com.fadali.flod_restaurant.presentation.ui.theme.grayText
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel(),
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogMessage by rememberSaveable { mutableStateOf<String?>(null) }

    HandleState(viewModel.state) { state ->
        LoginScreenContent(
            state = state,
            sendEvent = { event -> viewModel.sendEvent(event) }
        )
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            LoginEffect.NavigateToSignUp -> navController.navigate(AuthRoutes.SignUp)
            is LoginEffect.NavigateToOtp -> navController.navigate(
                AuthRoutes.OtpVerification(
                    flowType = OtpFlowType.LOGIN,
                    userId = effect.userId,
                    phone = effect.phoneNumber
                )
            )
            is LoginEffect.ShowError -> {
                showErrorDialog = true
                errorDialogMessage = effect.message
            }
        }
    }
    AppErrorDialog(
        isVisible = showErrorDialog,
        title = stringResource(R.string.error_occurred),
        message = errorDialogMessage ?: stringResource(R.string.something_went_wrong),
        primaryButtonText = stringResource(R.string.try_again),
        secondaryButtonText = stringResource(R.string.cancel),
        isDismissible = true,
        onPrimaryBtnClicked = { viewModel.sendEvent(LoginEvent.OnLoginClicked) },
        onDismiss = { showErrorDialog = false }
    )
}

@Composable
private fun LoginScreenContent(
    state: LoginState,
    sendEvent: (LoginEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clearFocusOnTap(focusManager = focusManager)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLoadingDialog(isVisible = state.isLoading)

        Image(
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.End)
                .clickable { sendEvent(LoginEvent.OnLanguageSelectorClicked) },
            painter = painterResource(R.drawable.ic_globe),
            contentDescription = "Change Language"
        )
        Text(
            text = stringResource(R.string.brand),
            style = MaterialTheme.typography.displaySmall.copy(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.accentBlue,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.sign_in_description),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.grayText,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(36.dp))
        AppPhoneNumberField(
            phoneNumber = state.phoneNumber,
            onPhoneNumberChanged = { sendEvent(LoginEvent.OnPhoneNumberChanged(it)) },
            onFocusChanged = { isFocused ->
                if (!isFocused) sendEvent(LoginEvent.ValidatePhoneNumber)
                else sendEvent(LoginEvent.RemovePhoneNumberValidationError)
            },
            onDone = { sendEvent(LoginEvent.OnLoginClicked) },
            validationError = state.phoneNumberError?.let { stringResource(it) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        AppPrimaryButton(
            onClick = { sendEvent(LoginEvent.OnLoginClicked) },
            text = stringResource(R.string.login)
        )
        Spacer(modifier = Modifier.height(38.dp))
    }
}

@AppPreviews
@Composable
private fun LoginScreenContentPreview() {
    FLODRestaurantTheme {
        LoginScreenContent(
            state = LoginState(),
            sendEvent = {}
        )
    }
}
