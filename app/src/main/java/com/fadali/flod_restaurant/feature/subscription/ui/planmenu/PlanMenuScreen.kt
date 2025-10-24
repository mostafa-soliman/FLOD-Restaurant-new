package com.fadali.flod_restaurant.feature.subscription.ui.planmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.navigateBack
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.card.AppMealPlanHorizontalCard
import com.fadali.flod_restaurant.core.component.dashedline.DashedDivider
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.topBar.AppTopBar
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.i18n.LText
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.util.Dimens
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun PlanMenuScreen(
    navController: NavController,
    viewModel: PlanMenuViewModel = koinViewModel(),
    args: SubscriptionRoutes.PlanMenu
) {
    LaunchedEffect(Unit) {
        viewModel.sendEvent(PlanMenuEvent.LoadData(args))
    }
    HandleState(viewModel.state) { state ->
        FLODRestaurantTheme {
            PlanMenuScreenContent(
                state = state,
                sendEvent = { event -> viewModel.handleEvent(event) }
            )
        }
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            PlanMenuEffect.NavigateBack -> {
                navController.navigateBack()
            }
        }
    }
}

@Composable
private fun PlanMenuScreenContent(
    state: PlanMenuState,
    sendEvent: (PlanMenuEvent) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = LText(R.string.view_plan_menu),
                onBackClick = { sendEvent(PlanMenuEvent.OnBackPressed) },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Dimens.ScreenPadding)
        ) {
            Text(
                text = stringResource(R.string.package_duration),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                state.mealsByDay?.entries?.forEachIndexed { dayIndex, (day, meals) ->
                    item(key = "day_$day") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(25.dp)
                                    .width(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_checkmark),
                                    contentDescription = null,
                                    tint = androidx.compose.ui.graphics.Color.Unspecified,
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(R.string.plan_menu_day_group, day),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                    itemsIndexed(
                        items = meals,
                        key = { index, meal -> "meal_${day}_${meal.id}_$index" }
                    ) { index, meal ->
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.width(40.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    DashedDivider(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .height(35.dp)
                                            .padding(bottom = 1.dp),
                                        color = MaterialTheme.colorScheme.secondary,
                                        thickness = 1.dp,
                                        dashLength = 2.dp,
                                        gapLength = 2.dp
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .background(
                                                MaterialTheme.colorScheme.secondary,
                                                CircleShape
                                            )
                                    )
                                    if (index < meals.size - 1 || dayIndex < state.mealsByDay.size - 1) {
                                        DashedDivider(
                                            modifier = Modifier
                                                .width(2.dp)
                                                .height(35.dp)
                                                .padding(top = 1.dp),
                                            color = MaterialTheme.colorScheme.secondary,
                                            thickness = 1.dp,
                                            dashLength = 2.dp,
                                            gapLength = 2.dp
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                AppMealPlanHorizontalCard(
                                    imageUrl = meal.imageUrl,
                                    name = meal.name,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(70.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        AppLoadingDialog(state.isLoading)
    }
}

@AppPreviews
@Composable
fun PlanMenuScreenPreview() {
    FLODRestaurantTheme {
        PlanMenuScreenContent(
            state = PlanMenuState(
                mealsByDay = linkedMapOf(
                    "1" to listOf(
                        MealItemData(
                            id = "1",
                            name = "Vegetable salad",
                            imageUrl = "",
                            quantity = "1 serving",
                            price = 9.99
                        ),
                        MealItemData(
                            id = "2",
                            name = "Beef Burger",
                            imageUrl = "",
                            quantity = "1 serving",
                            price = 4.99
                        ),
                        MealItemData(
                            id = "3",
                            name = "Nashville Chicks",
                            imageUrl = "",
                            quantity = "1 serving",
                            price = 7.99
                        )
                    ),
                    "2" to listOf(
                        MealItemData(
                            id = "4",
                            name = "fruit salad",
                            imageUrl = "",
                            quantity = "1 serving",
                            price = 6.99
                        ),
                        MealItemData(
                            id = "5",
                            name = "Shrimp pasta",
                            imageUrl = "",
                            quantity = "1 serving",
                            price = 12.99
                        ),
                        MealItemData(
                            id = "6",
                            name = "Strawberry pudding",
                            imageUrl = "",
                            quantity = "1 serving",
                            price = 5.99
                        )
                    )
                ),
                isLoading = false
            ),
            sendEvent = { /* no-op for preview */ }
        )
    }
}