package com.fadali.flod_restaurant.feature.plan.ui.packagedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.navigateBack
import com.fadali.flod_restaurant.app.navigation.routes.PlanRoutes
import com.fadali.flod_restaurant.core.component.card.AppAddOnCard
import com.fadali.flod_restaurant.core.component.card.AppMenuPackageCard
import com.fadali.flod_restaurant.core.component.card.AppPackageDayCard
import com.fadali.flod_restaurant.core.component.card.NutrientType
import com.fadali.flod_restaurant.core.component.card.PackageCardType
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.topBar.AppTopBar
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.feature.plan.ui.model.AddOnUiModel
import com.fadali.flod_restaurant.feature.plan.ui.model.MenuItemUiModel
import com.fadali.flod_restaurant.feature.plan.ui.model.PackageDayUiModel
import com.fadali.flod_restaurant.feature.plan.ui.model.PackageInfoUiModel
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun PackageDetailsScreen(
    navController: NavController,
    args: PlanRoutes.PackageDetails,
    viewModel: PackageDetailsViewModel = koinViewModel()
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(PackageDetailsEvent.Initialize(args.subscriptionId))
    }

    HandleState(viewModel.state) { state ->
        AppLoadingDialog(state.isLoading)
        PackageDetailsContent(
            state = state,
            sendEvent = viewModel::sendEvent
        )
    }

    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            PackageDetailsEffect.NavigateBack -> navController.navigateBack()
            is PackageDetailsEffect.ShowError -> {
                showErrorDialog = true
                errorDialogState = effect.dialogState ?: ErrorDialogState.default
            }

            is PackageDetailsEffect.NavigateToCustomize -> {
                // TODO: Navigate to customize screen
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
        })
}

@Composable
private fun PackageDetailsContent(
    state: PackageDetailsState,
    sendEvent: (PackageDetailsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        AppTopBar(
            title = stringResource(R.string.package_plan),
            onBackClick = { sendEvent(PackageDetailsEvent.OnBackClick) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            state.packageInfo?.let { packageInfo ->
                AppMenuPackageCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    imageUrl = packageInfo.imageUrl,
                    title = packageInfo.title,
                    buttonText = stringResource(R.string.freeze_package),
                    onButtonClick = {
                        state.subscriptionId?.let {
                            sendEvent(PackageDetailsEvent.OnFreezePackageClick(it))
                        }
                    },
                    cardType = PackageCardType.SUBSCRIPTION_SUMMARY,
                    dateRange = packageInfo.dateRange,
                    daysCount = packageInfo.daysCount,
                    statusLabel = packageInfo.statusLabel,
                    isPickup = packageInfo.isPickup,
                    mealsCount = packageInfo.mealsCount
                )
            }
            Column {
                Text(
                    text = "Package Days",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(state.packageDays) { index, day ->
                        AppPackageDayCard(
                            day = day.day,
                            month = day.month,
                            isSelected = state.selectedDayIndex == index,
                            onDayClick = {
                                sendEvent(PackageDetailsEvent.OnDaySelected(index))
                            })
                    }
                }
            }
            Column {
                Text(
                    text = getMenuTitle(
                        state.packageDays.getOrNull(state.selectedDayIndex)
                            ?: PackageDayUiModel(day = "", month = "")
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.todayMenuItems) { menuItem ->
                        AppMenuPackageCard(
                            modifier = Modifier.width(360.dp),
                            imageUrl = menuItem.imageUrl,
                            title = menuItem.title,
                            subtitle = menuItem.mealNumber,
                            buttonText = stringResource(R.string.customize),
                            onButtonClick = {
                                sendEvent(PackageDetailsEvent.OnCustomizeMenuClick(menuItem.id))
                            },
                            cardType = PackageCardType.SUBSCRIPTION_ITEM,
                            isPickup = menuItem.isPickup ?: false,
                            showNutrientCard = true,
                            nutrientValues = listOfNotNull(
                                menuItem.calories?.toString()?.let { it to NutrientType.CALORIES },
                                menuItem.protein?.let { it to NutrientType.PROTEIN },
                                menuItem.carbs?.let { it to NutrientType.CARBS },
                                menuItem.fat?.let { it to NutrientType.FAT }
                            )
                        )
                    }
                }
            }
            Column {
                Text(
                    text = stringResource(R.string.add_ons),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.addOns) { addOn ->
                        AppAddOnCard(
                            imageUrl = addOn.imageUrl,
                            name = addOn.name,
                            calories = addOn.calories,
                            price = addOn.price,
                            quantity = addOn.quantity,
                            onQuantityChange = { newQuantity ->
                                sendEvent(
                                    PackageDetailsEvent.OnAddOnQuantityChange(
                                        addOn.id, newQuantity
                                    )
                                )
                            },
                            hideQuantityControls = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun getMenuTitle(selectedDay: PackageDayUiModel): String {
    val today = LocalDate.now()
    val dayInt = selectedDay.day.toIntOrNull()
    val monthShort = selectedDay.month
    val todayDay = today.dayOfMonth
    val todayMonth = today.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    return if (dayInt == todayDay && monthShort.equals(todayMonth, ignoreCase = true)) {
        stringResource(R.string.today_menu)
    } else {
        stringResource(R.string.menu_for_day, selectedDay.day, selectedDay.month)
    }
}


@AppPreviews
@Composable
private fun PackageDetailsContentPreview_TodaySelected() {
    FLODRestaurantTheme {
        // Get today's date for preview
        val today = LocalDate.now()
        val todayDayOfMonth = today.dayOfMonth.toString()
        val todayMonthShort = today.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())

        val sampleState = PackageDetailsState(
            isLoading = false,
            subscriptionId = "sub_123",
            packageInfo = PackageInfoUiModel(
                imageUrl = "https://via.placeholder.com/150",
                title = "Healthy Variety Package",
                dateRange = "1 May - 7 May",
                daysCount = "7 Days",
                statusLabel = "Active",
                isPickup = false,
                mealsCount = "21 Meals"
            ),
            packageDays = listOf(
                PackageDayUiModel(day = "23", month = "Oct"),
                PackageDayUiModel(day = "24", month = "Oct"),
                PackageDayUiModel(day = todayDayOfMonth, month = todayMonthShort), // Today
                PackageDayUiModel(day = "26", month = "Oct"),
                PackageDayUiModel(day = "27", month = "Oct"),
                PackageDayUiModel(day = "28", month = "Oct"),
                PackageDayUiModel(day = "29", month = "Oct")
            ),
            selectedDayIndex = 2, // Today is selected - should show "Today's Menu"
            todayMenuItems = listOf(
                MenuItemUiModel(
                    id = "menu_1",
                    imageUrl = "https://via.placeholder.com/150",
                    title = "Grilled Chicken Salad",
                    mealNumber = "Meal 1",
                    calories = 350
                ),
                MenuItemUiModel(
                    id = "menu_2",
                    imageUrl = "https://via.placeholder.com/150",
                    title = "Quinoa Bowl",
                    mealNumber = "Meal 2",
                    calories = 400
                )
            ),
            addOns = listOf(
                AddOnUiModel(
                    id = "addon_1",
                    imageUrl = "https://via.placeholder.com/150",
                    name = "Protein Shake",
                    calories = 200,
                    price = 5.99,
                    quantity = 1
                ),
                AddOnUiModel(
                    id = "addon_2",
                    imageUrl = "https://via.placeholder.com/150",
                    name = "Fruit Cup",
                    calories = 150,
                    price = 3.49,
                    quantity = 0
                )
            )
        )

        PackageDetailsContent(
            state = sampleState, sendEvent = {}
        )
    }
}

@AppPreviews
@Composable
private fun PackageDetailsContentPreview_OtherDaySelected() {
    FLODRestaurantTheme {
        val sampleState = PackageDetailsState(
            isLoading = false,
            subscriptionId = "sub_123",
            packageInfo = PackageInfoUiModel(
                imageUrl = "https://via.placeholder.com/150",
                title = "Healthy Variety Package",
                dateRange = "1 May - 7 May",
                daysCount = "7 Days",
                statusLabel = "Active",
                isPickup = false,
                mealsCount = "21 Meals"
            ),
            packageDays = listOf(
                PackageDayUiModel(day = "23", month = "Oct"),
                PackageDayUiModel(day = "24", month = "Oct"),
                PackageDayUiModel(day = "25", month = "Oct"),
                PackageDayUiModel(day = "26", month = "Oct"), // Selected but not today
                PackageDayUiModel(day = "27", month = "Oct"),
                PackageDayUiModel(day = "28", month = "Oct"),
                PackageDayUiModel(day = "29", month = "Oct")
            ),
            selectedDayIndex = 3, // 26 Oct is selected - should show "26 Oct Menu"
            todayMenuItems = listOf(
                MenuItemUiModel(
                    id = "menu_1",
                    imageUrl = "https://via.placeholder.com/150",
                    title = "Grilled Chicken Salad",
                    mealNumber = "Meal 1",
                    calories = 350
                ),
                MenuItemUiModel(
                    id = "menu_2",
                    imageUrl = "https://via.placeholder.com/150",
                    title = "Quinoa Bowl",
                    mealNumber = "Meal 2",
                    calories = 400
                )
            ),
            addOns = listOf(
                AddOnUiModel(
                    id = "addon_1",
                    imageUrl = "https://via.placeholder.com/150",
                    name = "Protein Shake",
                    calories = 200,
                    price = 5.99,
                    quantity = 1
                ),
                AddOnUiModel(
                    id = "addon_2",
                    imageUrl = "https://via.placeholder.com/150",
                    name = "Fruit Cup",
                    calories = 150,
                    price = 3.49,
                    quantity = 0
                )
            )
        )

        PackageDetailsContent(
            state = sampleState, sendEvent = {}
        )
    }
}
