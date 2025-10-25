package com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.navigateBack
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.component.image.AppNetworkImage
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.topBar.AppTopBar
import com.fadali.flod_restaurant.core.network.appconfig.AppConfig
import com.fadali.flod_restaurant.core.network.appconfig.DayOfWeek
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails
import com.fadali.flod_restaurant.feature.subscription.domain.model.timeslots.DeliveryTimeSlot
import com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate.components.DaysSelectionSection
import com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate.components.DeliveryTimeSelectionSection
import com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate.components.PackageInformationSection
import com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate.components.PlanStartingDateSection
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlanStartingDateScreen(
    navController: NavController,
    viewModel: PlanStartingDateViewModel = koinViewModel(),
    args: SubscriptionRoutes.PlanStartingDate
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(PlanStartingDateUiEvent.LoadData(args = args))
    }
    HandleState(viewModel.state) { state ->
        PlanStartingDateScreenContent(
            state = state,
            sendEvent = { event -> viewModel.handleEvent(event) }
        )
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            is PlanStartingDateUiEffect.NavigateToNextScreen -> navController.navigate(effect.route)
            PlanStartingDateUiEffect.NavigateBack -> navController.navigateBack()
            is PlanStartingDateUiEffect.ShowError -> {
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
private fun PlanStartingDateScreenContent(
    state: PlanStartingDateState,
    sendEvent: (PlanStartingDateUiEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AppLoadingDialog(isVisible = state.isLoading)

        AppNetworkImage(
            modifier = Modifier
                .fillMaxHeight(0.45f)
                .fillMaxWidth(),
            model = R.drawable.startingdate_img,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )
        AppTopBar(
            modifier = Modifier.statusBarsPadding(),
            title = stringResource(if (state.args?.packageType == PackageType.PICKUP) R.string.pickup else R.string.delivery),
            onBackClick = { sendEvent(PlanStartingDateUiEvent.OnBackPressed) },
            textColor = colorScheme.onPrimary,
            backButtonTint = Color.Unspecified
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomCenter)
                .clip(
                    RoundedCornerShape(
                        topStart = 27.dp,
                        topEnd = 27.dp
                    )
                )
                .background(colorScheme.background)
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp)
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            PackageInformationSection(
                categoryName = state.args?.productCategory?.displayName ?: "",
                durationDays = state.args?.packageDetails?.durationDays ?: -1,
                mealsPerDay = state.args?.packageDetails?.mealsPerDay ?: -1
            )
            PlanStartingDateSection(
                modifier = Modifier.padding(top = 16.dp),
                selectedDateIndex = state.selectedStartingDateIndex,
                onDurationSelected = { sendEvent(PlanStartingDateUiEvent.OnStartingDateSelected(it)) },
                startingDates = state.displayableStartingDates
            )
            DaysSelectionSection(
                modifier = Modifier.padding(top = 20.dp),
                selectedDays = state.selectedDays,
                onDaysChanged = { days ->
                    sendEvent(PlanStartingDateUiEvent.OnSelectedDaysChanged(days))
                },
                daysOfWeek = state.daysOfWeek,
                isPickup = state.args?.packageType == PackageType.PICKUP
            )
            if (state.args?.packageType == PackageType.DELIVERY) {
                DeliveryTimeSelectionSection(
                    selectedTimeSlotIndex = state.selectedTimeSlotIndex,
                    onTimeSlotSelected = { sendEvent(PlanStartingDateUiEvent.OnTimeSlotSelected(it)) },
                    timeSlots = state.timeSlots.map {
                        stringResource(
                            id = R.string.time_slot,
                            it.startTime,
                            it.endTime
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AppPrimaryButton(
                modifier = Modifier.padding(vertical = 16.dp),
                text = stringResource(R.string.continue_text),
                onClick = { sendEvent(PlanStartingDateUiEvent.OnContinueBtnClicked) },
                enabled = state.isContinueBtnEnabled
            )
        }
    }
}

@Composable
@AppPreviews
private fun PlanStartingDateScreenContentPreview() {
    val mockState = PlanStartingDateState(
        selectedStartingDateIndex = 1,
        selectedDays = setOf(DayOfWeek.FRIDAY),
        selectedTimeSlotIndex = 0,
        displayableStartingDates = listOf("12 Aug", "13 Aug", "14 Aug", "15 Aug", "16 Aug"),
        daysOfWeek = AppConfig.default.daysOfWeek,
        timeSlots = listOf(
            DeliveryTimeSlot(
                id = "1",
                startTime = "10:00 AM",
                endTime = "12:00 PM",
                timeSlotType = 0
            ),
            DeliveryTimeSlot(
                id = "1",
                startTime = "10:00 AM",
                endTime = "12:00 PM",
                timeSlotType = 0
            ),
        ),
        args = SubscriptionRoutes.PlanStartingDate(
            productCategory = ProductCategory.empty.copy(displayName = "Chicken"),
            packageDetails = PackageDetails(
                id = "pkg_123",
                durationDays = 30,
                mealsPerDay = 3,
                price = 199.99,
                title = "Chicken Plan",
                description = "A healthy chicken meal plan.",
                imageUrl = "",
                nutritionInfo = null,
                branches = emptyList(),
            ),
            packageType = PackageType.DELIVERY,
            deliveryAddress = null,
            pickupBranch = null,
        )
    )
    FLODRestaurantTheme {
        PlanStartingDateScreenContent(
            state = mockState,
            sendEvent = {}
        )
    }
}
