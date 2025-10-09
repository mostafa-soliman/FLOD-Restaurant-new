package com.fadali.flod_restaurant.feature.auth.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.core.component.datepicker.AppDatePickerDialog
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.modifiers.clearFocusOnTap
import com.fadali.flod_restaurant.core.component.multiselect.AppGenderSelector
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.textfield.AppTextField
import com.fadali.flod_restaurant.feature.auth.ui.otp.OtpFlowType
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue
import com.fadali.flod_restaurant.presentation.ui.theme.appText
import com.fadali.flod_restaurant.presentation.ui.theme.grayText
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = koinViewModel(),
    args: AuthRoutes.SignUp
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogMessage by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(SignUpEvent.LoadArgs(args))
        viewModel.sendEvent(SignUpEvent.LoadBirthDateRange)
    }
    HandleState(viewModel.state) { state ->
        SignUpScreenContent(
            state = state,
            sendEvent = { event -> viewModel.sendEvent(event) }
        )
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            is SignUpEffect.NavigateToOtp -> navController.navigate(
                AuthRoutes.OtpVerification(
                    flowType = OtpFlowType.SIGN_UP,
                    phone = effect.phoneNumber
                )
            )
            is SignUpEffect.ShowError -> {
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
        onPrimaryBtnClicked = { viewModel.sendEvent(SignUpEvent.OnNextBtnClicked) },
        onDismiss = { showErrorDialog = false }
    )
}

@Composable
private fun SignUpScreenContent(
    state: SignUpState,
    sendEvent: (SignUpEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .clearFocusOnTap(focusManager = focusManager)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 38.dp, bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLoadingDialog(isVisible = state.isLoading)

        Text(
            stringResource(R.string.brand),
            style = MaterialTheme.typography.displaySmall.copy(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        )
        Spacer(Modifier.height(18.dp))
        Text(
            stringResource(R.string.signup_title),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.accentBlue,
            )
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.signup_subtitle),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.grayText
            )
        )
        Spacer(Modifier.height(20.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            label = stringResource(R.string.name_label),
            onValueChange = { sendEvent(SignUpEvent.OnNameChanged(it)) },
            keyboardType = KeyboardType.Text,
            onNext = {},
            maxLength = 50,
            maxLines = 1,
            onFocusChanged = { isFocused ->
                if (isFocused) sendEvent(SignUpEvent.OnRemoveNameValidationError)
                else sendEvent(SignUpEvent.OnValidateName)
            },
            errorMessage = state.nameError?.let{ stringResource(it) }
        )
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            label = stringResource(R.string.email_label),
            onValueChange = { sendEvent(SignUpEvent.OnEmailChanged(it)) },
            keyboardType = KeyboardType.Text,
            onNext = {},
            maxLength = 50,
            maxLines = 1,
            onFocusChanged = { isFocused ->
                if (isFocused) sendEvent(SignUpEvent.OnRemoveEmailValidationError)
                else sendEvent(SignUpEvent.OnValidateEmail)
            },
            errorMessage = state.emailError?.let { stringResource(it) }
        )
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.displayedBirthdate,
            label = stringResource(R.string.birthday_label),
            readOnly = true,
            placeholder = stringResource(R.string.birthday_date_picker_title),
            onFocusChanged = { isFocused ->
                if (!isFocused) sendEvent(SignUpEvent.OnValidateBirthdate)
                else sendEvent(SignUpEvent.OnRemoveBirthdateValidationError)
            },
            suffix = {
                Image(
                    modifier = Modifier.clickable {
                        sendEvent(SignUpEvent.OnBirthdatePickerClicked)
                    },
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar),
                    contentDescription = stringResource(R.string.pick_date_content_desc),
                )
            },
            errorMessage = state.birthdateError?.let { stringResource(it) }
        )
        AppDatePickerDialog(
            isVisible = state.isBirthdatePickerVisible,
            dateRange = state.birthDateRange,
            title = stringResource(R.string.birthday_date_picker_title),
            onDateSelected = { sendEvent(SignUpEvent.OnBirthdateSelected(it)) },
            onDismiss = { sendEvent(SignUpEvent.OnBirthdatePickerDismissed) }
        )
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(R.string.gender_label),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.appText,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
            )
        )
        Spacer(Modifier.height(12.dp))
        AppGenderSelector(
            modifier = Modifier.fillMaxWidth(),
            selectedGender = state.gender,
            onGenderSelected = { sendEvent(SignUpEvent.OnGenderSelected(it)) },
            errorMessage = state.genderError?.let { stringResource(it) }
        )
        Spacer(Modifier.weight(1f))
        AppPrimaryButton(
            text = stringResource(R.string.next),
            onClick = { sendEvent(SignUpEvent.OnNextBtnClicked) },
        )
    }
}

@AppPreviews
@Composable
private fun SignUpScreenContentPreview() {
    FLODRestaurantTheme {
        SignUpScreenContent(
            state = SignUpState(),
            sendEvent = {}
        )
    }
}
