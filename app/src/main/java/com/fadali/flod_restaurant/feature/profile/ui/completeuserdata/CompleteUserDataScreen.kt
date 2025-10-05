package com.fadali.flod_restaurant.feature.profile.ui.completeuserdata

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.component.modifiers.clearFocusOnTap
import com.fadali.flod_restaurant.core.component.multiselect.vertical.AppVerticalMultiSelector
import com.fadali.flod_restaurant.core.component.multiselect.vertical.VerticalMultiSelectorItem
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.textfield.AppTextField
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.feature.profile.domain.model.ActivityData
import com.fadali.flod_restaurant.feature.profile.domain.model.GoalData
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue
import com.fadali.flod_restaurant.presentation.ui.theme.appText
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CompleteUserDataScreen(
    navController: NavController,
    viewModel: CompleteUserDataViewModel = koinViewModel(),
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(UserDataEvent.LoadLookups)
    }
    HandleState(viewModel.state) { state ->
        CompleteUserDataScreenContent(
            state = state,
            sendEvent = { event -> viewModel.sendEvent(event) }
        )
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            UserDataEffect.NavigateToAllergies -> navController.navigate(AuthRoutes.Allergies)
            is UserDataEffect.ShowError -> {
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
private fun CompleteUserDataScreenContent(
    state: UserDataState,
    sendEvent: (UserDataEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .clearFocusOnTap(focusManager = focusManager)
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLoadingDialog(isVisible = state.isLoading)

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.fit_start_title),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.accentBlue,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(31.dp))
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(R.string.weight_label),
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.appText
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.weight,
            onValueChange = { sendEvent(UserDataEvent.OnWeightChange(it)) },
            keyboardType = KeyboardType.Decimal,
            onNext = {},
            maxLength = 6,
            maxLines = 1,
            onFocusChanged = { isFocused ->
                if (isFocused) sendEvent(UserDataEvent.OnRemoveWeightError)
                else sendEvent(UserDataEvent.OnValidateWeight)
            },
            errorMessage = state.weightError?.resolve(state.minWeight, state.maxWeight),
            suffix = {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_weight),
                    contentDescription = stringResource(R.string.weight_label)
                )
            }
        )
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(R.string.height_label),
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.appText
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.height,
            onValueChange = { sendEvent(UserDataEvent.OnHeightChange(it)) },
            keyboardType = KeyboardType.Decimal,
            onNext = {},
            maxLength = 6,
            maxLines = 1,
            onFocusChanged = { isFocused ->
                if (isFocused) sendEvent(UserDataEvent.OnRemoveHeightError)
                else sendEvent(UserDataEvent.OnValidateHeight)
            },
            errorMessage = state.heightError?.resolve(state.minHeight, state.maxHeight),
            suffix = {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_height),
                    contentDescription = stringResource(R.string.height_label)
                )
            }
        )
        AppVerticalMultiSelector(
            title = stringResource(R.string.activity_level_label),
            options = state.activityLevels.map {
                VerticalMultiSelectorItem(
                    id = it.id,
                    title = it.displayName,
                    imageUrl = it.imageUrl,
                    isSelected = it.id == state.selectedActivity?.id
                )
            },
            onItemSelected = { item ->
                val selectedActivity = state.activityLevels.firstOrNull { it.id == item.id }
                if (selectedActivity != null) sendEvent(UserDataEvent.OnActivityLevelSelected(selectedActivity))
            }
        )
        AppVerticalMultiSelector(
            title = stringResource(R.string.goals_label),
            options = state.goals.map {
                VerticalMultiSelectorItem(
                    id = it.id,
                    title = it.displayName,
                    imageUrl = it.imageUrl,
                    isSelected = it.id == state.selectedGoal?.id
                )
            },
            onItemSelected = { item ->
                val selectedGoal = state.goals.firstOrNull { it.id == item.id }
                if (selectedGoal != null) sendEvent(UserDataEvent.OnGoalSelected(selectedGoal))
            }
        )
        Spacer(Modifier.size(8.dp))
        AppPrimaryButton(
            text = stringResource(R.string.continue_label),
            onClick = { sendEvent(UserDataEvent.OnContinueClicked) },
        )
    }
}

@AppPreviews
@Composable
private fun CompleteUserDataScreenPreview() {
    FLODRestaurantTheme {
        CompleteUserDataScreenContent(
            state = UserDataState(
                activityLevels = listOf(
                    ActivityData("1", "Low", "https://via.placeholder.com/150"),
                    ActivityData("2", "Medium", "https://via.placeholder.com/150"),
                    ActivityData("3", "High", "https://via.placeholder.com/150"),
                ),
                goals = listOf(
                    GoalData("1", "Lose Weight", "https://via.placeholder.com/150"),
                    GoalData("2", "Maintain Weight", "https://via.placeholder.com/150"),
                    GoalData("3", "Gain Weight", "https://via.placeholder.com/150"),
                )
            ),
            sendEvent = {}
        )
    }
}
