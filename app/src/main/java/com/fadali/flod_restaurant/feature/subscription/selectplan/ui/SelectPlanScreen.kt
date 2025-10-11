package com.fadali.flod_restaurant.feature.subscription.selectplan.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.navigateBack
import com.fadali.flod_restaurant.core.component.card.AppCategoryChip
import com.fadali.flod_restaurant.core.component.card.AppDashedChoice
import com.fadali.flod_restaurant.core.component.card.AppPackageCard
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.component.image.AppShimmerBox
import com.fadali.flod_restaurant.core.component.image.CustomIcons
import com.fadali.flod_restaurant.core.component.multiselect.SegmentedTwoButtons
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.topBar.AppTopBar
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.feature.subscription.selectplan.ui.components.SubscriptionBranchLocationRow
import com.fadali.flod_restaurant.feature.subscription.selectplan.ui.components.TitleText
import com.fadali.flod_restaurant.feature.subscription.selectplan.ui.mapper.toPlanPackage
import com.fadali.flod_restaurant.feature.subscription.selectplan.ui.mockdata.SubscriptionMockData
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.util.Dimens
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SelectPlanScreen(
    navController: NavHostController,
    viewModel: SelectPlanViewModel = koinViewModel()
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(SelectPlanEvent.LoadData)
    }
    HandleState(viewModel.state) { state ->
        SelectPlanScreenContent(
            state = state,
            sendEvent = viewModel::sendEvent
        )
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            SelectPlanEffect.NavigateBack -> navController.navigateBack()
            SelectPlanEffect.NavigateToNextScreen -> TODO()
            is SelectPlanEffect.ShowError -> {
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
fun SelectPlanScreenContent(
    state: SelectPlanState,
    sendEvent: (SelectPlanEvent) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    Scaffold(
        modifier = Modifier.padding(top = 47.dp),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.select_plan),
                onBackClick = { sendEvent(SelectPlanEvent.OnBackBtnClicked) }
            )
        },
    ) { paddingValues ->
        AppLoadingDialog(state.isLoading)

        Crossfade(
            targetState = state.isLoading,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { loading ->
            if (loading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.ScreenPadding),
                    verticalArrangement = Arrangement.spacedBy(Dimens.CardSpacing)
                ) {
                    repeat(6) {
                        AppShimmerBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                        )
                    }
                    AppShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = Dimens.ScreenPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    SegmentedTwoButtons(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        leftLabel = stringResource(R.string.pick_up),
                        rightLabel = stringResource(R.string.delivery),
                        leftIcon = CustomIcons.Pickup,
                        rightIcon = CustomIcons.Delivery,
                        selectedLeft = state.packageType == PackageType.PICKUP,
                        onSelect = { type ->
                            sendEvent(SelectPlanEvent.OnSubscriptionTypeChanged(type))
                        }
                    )
                    TitleText(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(state.branchTitle)
                    )
                    SubscriptionBranchLocationRow(
                        modifier = Modifier.padding(top = 12.dp),
                        branchAddress = state.selectedBranch.displayName,
                        onEditBranchLocation = { /* TODO */ },
                    )
                    TitleText(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(state.categoriesTitle)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        state.categories.forEachIndexed { index, category ->
                            AppCategoryChip(
                                imageUrl = category.imageUrl ?: "",
                                label = category.displayName,
                                selected = state.selectedCategory.id == category.id,
                                onClick = { sendEvent(SelectPlanEvent.OnCategorySelected(category)) }
                            )
                        }
                    }
                    TitleText(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(R.string.package_duration)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        state.packageDurationList.forEachIndexed { index, duration ->
                            AppDashedChoice(
                                selected = state.selectedPackageDuration == duration,
                                onClick = { sendEvent(SelectPlanEvent.OnDurationSelected(duration)) },
                                modifier = Modifier
                                    .height(36.dp)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 8.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar_outline),
                                        modifier = Modifier.size(15.dp),
                                        contentDescription = null,
                                        tint = if (state.selectedPackageDuration == duration) colorScheme.primary else colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = stringResource(R.string.subscription_duration_text, duration),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = if (state.selectedPackageDuration == duration) colorScheme.primary else colorScheme.onSurface
                                        ),
                                        fontWeight = if (state.selectedPackageDuration == duration) FontWeight.SemiBold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                    TitleText(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(R.string.number_of_meals)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        state.numberOfMealsList.forEachIndexed { index, mealCount ->
                            AppDashedChoice(
                                selected = state.selectedNumberOfMeals == mealCount,
                                onClick = { sendEvent(SelectPlanEvent.OnMealsCountSelected(mealCount)) },
                                modifier = Modifier
                                    .height(36.dp)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = stringResource(R.string.subscription_meal_text, mealCount),
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 6.dp
                                    ),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (state.selectedNumberOfMeals == mealCount) colorScheme.primary else colorScheme.onSurface
                                    ),
                                    fontWeight = if (state.selectedNumberOfMeals == mealCount) FontWeight.SemiBold else FontWeight.Medium
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(22.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        state.filteredPackages.forEach { planPackage ->
                            AppPackageCard(
                                planPackage = planPackage.toPlanPackage(),
                                onBtnClicked = { /* TODO */ },
                                onCardClicked = {}
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                }
            }
        }
    }
}

@AppPreviews
@Composable
private fun SelectPlanScreenContentPreview() {
    FLODRestaurantTheme {
        SelectPlanScreenContent(
            state = SelectPlanState(
                isLoading = false,
                packageType = PackageType.PICKUP,
                categories = SubscriptionMockData.categories,
                selectedBranch = SubscriptionMockData.branches.first(),
                packageDurationList = listOf(7, 14, 30),
                numberOfMealsList = listOf(1, 2, 3),
                filteredPackages = SubscriptionMockData.packages,
                selectedPackageDuration = 7,
                selectedNumberOfMeals = 1,
            ),
            sendEvent = {}
        )
    }
}