package com.fadali.flod_restaurant.feature.subscription.ui.review

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.topBar.AppTopBar
import com.fadali.flod_restaurant.core.network.appconfig.DayOfWeek
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.feature.subscription.domain.model.addons.AddonNutritionInfo
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.NutritionInfo
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetailsBranch
import com.fadali.flod_restaurant.feature.subscription.domain.model.timeslots.DeliveryTimeSlot
import com.fadali.flod_restaurant.feature.subscription.ui.review.component.AddOnsSection
import com.fadali.flod_restaurant.feature.subscription.ui.review.component.ConfirmationInfoSection
import com.fadali.flod_restaurant.feature.subscription.ui.review.component.NutrientsSection
import com.fadali.flod_restaurant.feature.subscription.ui.review.component.PackageHeaderSection
import com.fadali.flod_restaurant.feature.subscription.ui.review.component.PaymentSection
import com.fadali.flod_restaurant.feature.subscription.ui.review.component.TermsAndConditionsRow
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.AddonUiModel
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.ConfirmationInfo
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.util.Dimens
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ReviewScreen(
    navController: NavController,
    viewModel: ReviewViewModel = koinViewModel(),
    args: SubscriptionRoutes.SubscriptionReview,
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(SubscriptionReviewEvent.LoadData(args = args))
    }
    HandleState(viewModel.state) { state ->
        ReviewScreenContent(
            state = state,
            sendEvent = viewModel::sendEvent,
        )
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            SubscriptionReviewEffect.NavigateBack -> navController.navigateBack()
            is SubscriptionReviewEffect.NavigateToNextScreen -> navController.navigate(effect.route)
            is SubscriptionReviewEffect.ShowError -> {
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
private fun ReviewScreenContent(
    state: SubscriptionReviewState,
    sendEvent: (SubscriptionReviewEvent) -> Unit
) {
    AppLoadingDialog(isVisible = state.isLoading)
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AppTopBar(
            title = stringResource(R.string.review),
            onBackClick = { sendEvent(SubscriptionReviewEvent.OnBackClicked) },
            modifier = Modifier.statusBarsPadding()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            PackageHeaderSection(
                modifier = Modifier.padding(top = 16.dp),
                packageDetails = state.args?.packageDetails,
            )
            Spacer(modifier = Modifier.height(20.dp))
            state.args?.packageDetails?.nutritionInfo?.let {
                NutrientsSection(
                    nutritionInfo = it,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            ConfirmationInfoSection(
                isPickup = state.args?.packageType == PackageType.PICKUP,
                confirmationInfo = state.confirmationInfo,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            AddOnsSection(
                addOns = state.addOns ?: emptyList(),
                onQuantityChange = { index, quantity ->
                    sendEvent(
                        SubscriptionReviewEvent.OnAddOnQuantityChange(
                            index = index,
                            quantity = quantity
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            PaymentSection(
                promoCode = state.promoCodeText,
                onPromoCodeChange = {
                    sendEvent(
                        SubscriptionReviewEvent.OnPromoCodeChange(
                            it
                        )
                    )
                },
                onApplyPromoCode = { sendEvent(SubscriptionReviewEvent.OnApplyPromoCode) },
                planPrice = state.args?.packageDetails?.price ?: 0.0,
                addOnPrice = state.totalAddonPrices.toDouble(),
                promoDiscount = state.promoCodeDiscount?.toDouble(),
                totalPrice = state.totalPrice.toDouble(),
                promoCodeValidationState = state.promoCodeValidationState
            )
            Spacer(modifier = Modifier.height(Dimens.CardSpacing))
            TermsAndConditionsRow(
                isTermsAccepted = state.isTermsAccepted,
                onTermsToggled = { sendEvent(SubscriptionReviewEvent.OnTermsToggled(it)) },
                onNavigateToTerms = { sendEvent(SubscriptionReviewEvent.OnNavigateToTermsAndConditions) }
            )
            Spacer(modifier = Modifier.height(Dimens.CardSpacing))
            AppPrimaryButton(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(R.string.confirm),
                enabled = state.isTermsAccepted,
                onClick = { sendEvent(SubscriptionReviewEvent.OnConfirmClicked) }
            )
        }
    }
}

@AppPreviews
@Composable
private fun SubscriptionConfirmationScreenPreview() {
    FLODRestaurantTheme {
        ReviewScreenContent(
            state = SubscriptionReviewState(
                isLoading = false,
                confirmationInfo = ConfirmationInfo(
                    mealsPerDay = 3,
                    numberOfDays = 30,
                    startingDate = "25 Aug",
                    deliveryTimeSlot = "11:00-15:00 PM",
                    pickUpBranchAddress = "Riyadh, Al olaya...",
                    streetName = "123",
                    buildingNumber = "123",
                    flatNumber = "106",
                    phoneNumber = "+966214365985"
                ),
                addOns = listOf(
                    AddonUiModel(
                        id = "1",
                        name = "Extra Chicken",
                        price = 10.5,
                        quantity = 2,
                        imageUrl = "",
                        nutritionInfo = AddonNutritionInfo(
                            calories = 300,
                            protein = 25.0,
                            carbs = 20.0,
                            fat = 10.0
                        )
                    )
                ),
                args = SubscriptionRoutes.SubscriptionReview(
                    packageType = PackageType.DELIVERY,
                    productCategory = ProductCategory(
                        id = "1",
                        displayName = "Chicken",
                        imageUrl = ""
                    ),
                    packageDetails = PackageDetails(
                        id = "1",
                        title = "Chicken Plan",
                        description = "A balanced meal plan for everyday health.",
                        price = 199.99,
                        durationDays = 30,
                        mealsPerDay = 3,
                        imageUrl = "",
                        nutritionInfo = NutritionInfo(
                            id = "1",
                            title = "",
                            calories = 1500,
                            protein = 100,
                            carbohydrates = 150,
                            fats = 50
                        ),
                        branches = listOf(
                            PackageDetailsBranch(
                                id = "",
                                title = "Olaya Branch",
                                deliveryArea = ""
                            )
                        )
                    ),
                    deliveryAddress = CustomerAddress.empty.copy(
                        streetName = "Olaya Street",
                        flatNumber = 12,
                        district = "Al Olaya",
                        city = "Riyadh",
                    ),
                    pickupBranch = Branch.empty.copy(displayName = "Olaya Branch"),
                    phoneNumber = "0512345678",
                    startingDateUtc = "2024-08-25T00:00:00Z",
                    displayableStartingDate = "25 Aug",
                    deliveryTimeSlot = DeliveryTimeSlot(
                        id = "1",
                        startTime = "11:00 AM",
                        endTime = "03:00 PM",
                        timeSlotType = 0
                    ),
                    selectedDays = DayOfWeek.entries
                ),
                promoCodeText = "FL0D20",
            ),
            sendEvent = {}
        )
    }
}
