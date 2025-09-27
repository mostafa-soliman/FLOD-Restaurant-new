package com.fadali.flod_restaurant.feature.auth.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.core.component.dialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.modifiers.clearFocusOnTap
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.textfield.AppTextField
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
            phoneNumber = state.phoneNumber,
            onPhoneNumberChanged = { phoneNumber ->
                viewModel.sendEvent(LoginEvent.OnPhoneNumberChanged(phoneNumber = phoneNumber))
            },
            onLoginClicked = {
                viewModel.sendEvent(LoginEvent.OnLoginClicked)
            },
            onSignUpClicked = {
                viewModel.sendEvent(LoginEvent.OnSignUpClicked)
            },
            onLanguageBtnClicked = {
                viewModel.sendEvent(LoginEvent.OnLanguageSelectorClicked)
            },
            onValidatePhoneNumber = {
                viewModel.sendEvent(LoginEvent.ValidatePhoneNumber)
            },
            onRemovePhoneNumberValidationError = {
                viewModel.sendEvent(LoginEvent.RemovePhoneNumberValidationError)
            },
            isLoading = state.isLoading,
            isValidPhoneNumber = state.isValidPhoneNumber,
        )
    }

    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            LoginEffect.NavigateToSignUp -> navController.navigate(AuthRoutes.SignUp)
            is LoginEffect.NavigateToOtp -> navController.navigate(AuthRoutes.OtpVerification(phone = effect.phoneNumber))
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
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit,
    onLoginClicked: (String) -> Unit,
    onSignUpClicked: () -> Unit,
    onLanguageBtnClicked: () -> Unit,
    onValidatePhoneNumber: () -> Unit,
    onRemovePhoneNumberValidationError: () -> Unit,
    isLoading: Boolean = false,
    isValidPhoneNumber: Boolean = true,
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
        AppLoadingDialog(isVisible = isLoading)

        Image(
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.End)
                .clickable { onLanguageBtnClicked() },
            painter = painterResource(R.drawable.ic_globe),
            contentDescription = "Change Language"
        )
        Text(
            text = stringResource(R.string.brand),
            style = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = stringResource(R.string.welcome_back),
            style = TextStyle(
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.accentBlue,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.sign_in_description),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.grayText,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(36.dp))
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                AppTextField(
                    modifier = Modifier.requiredWidth(110.dp),
                    value = "+966",
                    onValueChange = {},
                    enabled = false,
                    prefix = {
                        Image(
                            modifier = Modifier
                                .width(36.dp)
                                .height(24.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_saudi_flag),
                            contentDescription = stringResource(R.string.saudi_flag_content_desc)
                        )
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                AppTextField(
                    modifier = Modifier
                        .height(68.dp)
                        .fillMaxWidth(),
                    value = phoneNumber,
                    label = stringResource(R.string.phone_number),
                    onValueChange = { onPhoneNumberChanged(it) },
                    keyboardType = KeyboardType.Phone,
                    onDone = { onLoginClicked(phoneNumber) },
                    maxLength = 10,
                    onFocusChanged = { isFocused ->
                        if (isFocused) onRemovePhoneNumberValidationError()
                        else onValidatePhoneNumber()
                    },
                    errorMessage = if (isValidPhoneNumber) null else stringResource(R.string.invalid_phone_number)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        AppPrimaryButton(
            onClick = { onLoginClicked(phoneNumber) },
            text = stringResource(R.string.login)
        )
        Spacer(modifier = Modifier.height(38.dp))
        SignUpPrompt(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onSignUpClicked = onSignUpClicked
        )
    }
}

@Composable
private fun SignUpPrompt(
    modifier: Modifier = Modifier,
    onSignUpClicked: () -> Unit
) {
    val signUpPrompt = stringResource(R.string.signup_prompt)
    val clickableText = stringResource(R.string.signup_prompt_clickable_text)
    val signUpClickTag = "SIGN_UP"
    val annotatedText = buildAnnotatedString {
        pushStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.grayText,
            )
        )
        append(signUpPrompt.substringBefore(clickableText))
        pop()
        withLink(
            LinkAnnotation.Clickable(
                tag = signUpClickTag,
                linkInteractionListener = {
                    onSignUpClicked()
                }
            )
        ) {
            pushStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                )
            )
            append(clickableText)
            pop()
        }
    }

    Text(
        modifier = modifier,
        text = annotatedText,
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    )
}

@AppPreviews
@Composable
private fun LoginScreenContentPreview() {
    FLODRestaurantTheme {
        LoginScreenContent(
            phoneNumber = "01023920082",
            onPhoneNumberChanged = {},
            onLoginClicked = {},
            onSignUpClicked = {},
            onLanguageBtnClicked = {},
            onValidatePhoneNumber = {},
            onRemovePhoneNumberValidationError = {},
            isLoading = false
        )
    }
}
