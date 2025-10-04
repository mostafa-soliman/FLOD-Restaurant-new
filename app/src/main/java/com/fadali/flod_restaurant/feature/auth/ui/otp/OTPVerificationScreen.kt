package com.fadali.flod_restaurant.feature.auth.ui.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.navigateAndClearBackStack
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.text.AppCountdownTimer
import com.fadali.flod_restaurant.core.component.text.annotatedtext.AppAnnotatedText
import com.fadali.flod_restaurant.core.component.text.annotatedtext.ClickableTextSegment
import com.fadali.flod_restaurant.core.component.textfield.otpfield.AppOTPField
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue
import com.fadali.flod_restaurant.presentation.ui.theme.appText
import com.fadali.flod_restaurant.presentation.ui.theme.grayText
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OTPVerificationScreen(
    navController: NavController,
    viewModel: OTPVerificationViewModel = koinViewModel(),
    args: AuthRoutes.OtpVerification
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(OTPVerificationEvent.LoadArgs(args = args))
    }
    HandleState(viewModel.state) { state ->
        OTPVerificationScreenContent(
            state = state,
            sendEvent = { event -> viewModel.sendEvent(event) }
        )
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            is OTPVerificationEffect.NavigateToNextScreen -> navController.navigateAndClearBackStack(effect.route)
            is OTPVerificationEffect.ShowError -> {
                showErrorDialog = true
                effect.dialogState?.let { errorDialogState = it }
            }
        }
    }
    AppErrorDialog(
        isVisible = showErrorDialog,
        title = errorDialogState.title.resolve(),
        message = errorDialogState.message.resolve(),
        primaryButtonText = errorDialogState.primaryButtonText.resolve(),
        secondaryButtonText = errorDialogState.secondaryButtonText?.resolve(),
        isDismissible = errorDialogState.isDismissible,
        onPrimaryBtnClicked = errorDialogState.onPrimaryBtnClicked,
        onDismiss = {
            showErrorDialog = false
            errorDialogState.onDismiss()
        }
    )
}

@Composable
private fun OTPVerificationScreenContent(
    state: OTPVerificationState,
    sendEvent: (OTPVerificationEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp, vertical = 38.dp),
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
            stringResource(R.string.verification_title),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.accentBlue,
            )
        )
        Spacer(Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(horizontal = 30.dp),
            text = stringResource(R.string.verification_message, state.phoneNumber),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.grayText,
                textAlign = TextAlign.Center
            )
        )
        Spacer(Modifier.height(50.dp))
        AppOTPField(
            onValueChange = { sendEvent(OTPVerificationEvent.OnOTPChanged(otp = it)) },
            onComplete = { sendEvent(OTPVerificationEvent.OnCompleteOTP) },
        )
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppAnnotatedText(
                segments = listOf(
                    ClickableTextSegment(text = stringResource(R.string.didnt_receive_code)),
                    ClickableTextSegment(text = " "),
                    ClickableTextSegment(
                        text = stringResource(R.string.resend),
                        tag = stringResource(R.string.resend),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = when {
                                state.isResendEnabled -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                            },
                        ).toSpanStyle(),
                        onClick = { sendEvent(OTPVerificationEvent.OnResendClicked) }
                    )
                ),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.appText,
                ),
                onTextClick = null
            )
            AppCountdownTimer(
                initialTimeInSeconds = state.timeLeft,
                onTimerFinished = { sendEvent(OTPVerificationEvent.OnTimerFinished) },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                ),
                isRunning = state.isResendEnabled.not()
            )
        }
    }
}


@AppPreviews
@Composable
private fun OTPVerificationScreenContentPreview() {
    FLODRestaurantTheme {
        OTPVerificationScreenContent(
            state = OTPVerificationState(),
            sendEvent = {}
        )
    }
}
