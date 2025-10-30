package com.fadali.flod_restaurant.feature.plan.ui.myplans

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.feature.plan.ui.myplans.model.PlanItem
import com.fadali.flod_restaurant.feature.plan.ui.myplans.model.PlanStatus
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyPlansScreen(
    navController: NavController,
    viewModel: MyPlansViewModel = koinViewModel(),
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(MyPlansEvent.LoadPlans)
    }

    HandleState(viewModel.state) { state ->
        MyPlansScreenContent(
            state = state,
            sendEvent = { event -> viewModel.sendEvent(event) }
        )
    }

    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            MyPlansEffect.NavigateBack -> navController.navigateBack()
            MyPlansEffect.NavigateToSelectPlan -> {
                navController.navigate(SubscriptionRoutes.SelectPlan)
            }

            is MyPlansEffect.ShowError -> {
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
private fun MyPlansScreenContent(
    state: MyPlansState,
    sendEvent: (MyPlansEvent) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        AppLoadingDialog(isVisible = state.isLoading)

        AppTopBar(
            title = stringResource(R.string.subscription_status_title),
            onBackClick = { sendEvent(MyPlansEvent.OnBackClicked) },
            modifier = Modifier.statusBarsPadding()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            MyPlansTabRow(
                selectedTab = state.selectedTab,
                onTabSelected = { tab ->
                    sendEvent(MyPlansEvent.OnTabSelected(tab))
                },
                state = state
            )

            Spacer(modifier = Modifier.height(20.dp))

            val currentList = when (state.selectedTab) {
                PlanStatus.ACTIVE -> state.activePlans
                PlanStatus.EXPIRED -> state.expiredPlans
                PlanStatus.CANCELLED -> state.cancelledPlans
            }

            if (currentList.isEmpty()) {
                EmptyStateContent(
                    selectedTab = state.selectedTab,
                    onSubscribeNowClicked = {
                        sendEvent(MyPlansEvent.OnSubscribeNowClicked)
                    }
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(currentList) { plan ->
                        when (state.selectedTab) {
                            PlanStatus.ACTIVE -> {
                                ActivePlanCard(plan = plan,
                                    onClick = {
                                       // sendEvent(MyPlansEvent.OnPlanClicked(plan.id))
                                    })
                            }

                            PlanStatus.EXPIRED, PlanStatus.CANCELLED -> {
                                InactivePlanCard(
                                    plan = plan,
                                    onOrderAgainClick = {
                                        // TODO: Navigate to subscription plans with this plan pre-selected
                                    }
                                )
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MyPlansTabRow(
    selectedTab: PlanStatus,
    onTabSelected: (PlanStatus) -> Unit,
    state: MyPlansState
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MyPlansTab(
            icon = R.drawable.ic_check,
            label = stringResource(R.string.tab_active),
            isSelected = selectedTab == PlanStatus.ACTIVE,
            selectedColor = Color(0xFF4CAF50),
            onClick = { onTabSelected(PlanStatus.ACTIVE) },
            enabled = true,
            modifier = Modifier.weight(1f)
        )

        MyPlansTab(
            icon = R.drawable.ic_hourglass,
            label = stringResource(R.string.tab_expired),
            isSelected = selectedTab == PlanStatus.EXPIRED,
            selectedColor = Color(0xFF9E9E9E),
            onClick = { onTabSelected(PlanStatus.EXPIRED) },
            enabled = state.expiredPlans.isNotEmpty(),
            modifier = Modifier.weight(1f)
        )

        MyPlansTab(
            icon = R.drawable.ic_close,
            label = stringResource(R.string.tab_cancelled),
            isSelected = selectedTab == PlanStatus.CANCELLED,
            selectedColor = Color(0xFFFF5252),
            onClick = { onTabSelected(PlanStatus.CANCELLED) },
            enabled = state.cancelledPlans.isNotEmpty(),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun MyPlansTab(
    icon: Int,
    label: String,
    isSelected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val backgroundColor = if (isSelected) selectedColor else Color.White
    val contentColor = if (isSelected) Color.White else selectedColor
    val borderColor = if (isSelected) selectedColor else Color(0xFFE0E0E0)

    Box(
        modifier = modifier
            .height(48.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(enabled = enabled) { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = contentColor
                )
            )
        }
    }
}

@Composable
private fun EmptyStateContent(
    selectedTab: PlanStatus,
    onSubscribeNowClicked: () -> Unit
) {
    val messageResId = when (selectedTab) {
        PlanStatus.ACTIVE -> R.string.no_active_plans_message
        PlanStatus.EXPIRED -> R.string.no_expired_plans_message
        PlanStatus.CANCELLED -> R.string.no_cancelled_plans_message
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = buildAnnotatedString {
                val fullText = stringResource(messageResId)
                val parts = fullText.split(",")
                if (parts.size == 2) {
                    append(parts[0])
                    append(" , ")
                    withStyle(style = SpanStyle(color = Color(0xFF8B0000))) {
                        append(parts[1].trim())
                    }
                } else {
                    append(fullText)
                }
            },
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF616161),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        if (selectedTab == PlanStatus.ACTIVE) {
            AppPrimaryButton(
                text = stringResource(R.string.subscribe_now),
                onClick = onSubscribeNowClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ActivePlanCard(
    plan: PlanItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(color = Color(0xFFECECEC), shape = RoundedCornerShape(size = 15.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = if (plan.imageUrl.isEmpty()) R.drawable.startingdate_img else R.drawable.startingdate_img),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .background(Color.LightGray, RoundedCornerShape(15.dp)),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = plan.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
                maxLines = 1
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar_outline),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = plan.planDuration,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    )
                }
                Text(
                    text = plan.mealsCount,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier.width(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    Color(0xFF0AA6B5),
                                    androidx.compose.foundation.shape.CircleShape
                                )
                        )
                        com.fadali.flod_restaurant.core.component.dashedline.DashedDivider(
                            modifier = Modifier
                                .width(2.dp)
                                .height(30.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            thickness = 1.dp,
                            dashLength = 4.dp,
                            gapLength = 4.dp
                        )
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    Color(0xFF0AA6B5),
                                    androidx.compose.foundation.shape.CircleShape
                                )
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(11.dp)) {
                        Text(
                            text = plan.startDate,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF0AA6B5)
                            )
                        )
                        Text(
                            text = "End: ${plan.endDate}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF0AA6B5)
                            )
                        )
                    }
                }


                Icon(
                    painter = painterResource(id = if (plan.isPickup) R.drawable.ic_pickup else R.drawable.ic_delivery_car),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun InactivePlanCard(
    plan: PlanItem,
    onOrderAgainClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFECECEC), shape = RoundedCornerShape(size = 15.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = if (plan.imageUrl.isEmpty()) R.drawable.startingdate_img else R.drawable.startingdate_img),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .background(Color.LightGray, RoundedCornerShape(15.dp)),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = plan.title.uppercase(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
                maxLines = 1
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar_outline),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = plan.planDuration,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    )
                }
                Text(
                    text = plan.mealsCount,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
            }

            AppPrimaryButton(
                onClick = onOrderAgainClick,
                text = stringResource(R.string.order_again),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                containerColor = Color(0xFF8B0000),
                contentColor = Color.White
            )
        }
    }
}

@AppPreviews
@Composable
fun MyPlansScreenPreview() {
    FLODRestaurantTheme {
        MyPlansScreenContent(
            state = MyPlansState(
                selectedTab = PlanStatus.ACTIVE,
                activePlans = listOf(
                    PlanItem(
                        id = "1",
                        imageUrl = "",
                        title = "Donuts",
                        planDuration = "20 Days plan",
                        mealsCount = "x3 Meals",
                        startDate = "Start: 01 May",
                        endDate = "End: 15 May",
                        isPickup = false,
                        status = PlanStatus.ACTIVE
                    )
                )
            ),
            sendEvent = {}
        )
    }
}

@AppPreviews
@Composable
fun MyPlansScreenExpiredPreview() {
    FLODRestaurantTheme {
        MyPlansScreenContent(
            state = MyPlansState(
                selectedTab = PlanStatus.EXPIRED,
                expiredPlans = listOf(
                    PlanItem(
                        id = "1",
                        imageUrl = "",
                        title = "VARIETY PACKAGE",
                        planDuration = "20 Days plan",
                        mealsCount = "x3 Meals",
                        startDate = "",
                        endDate = "",
                        isPickup = false,
                        status = PlanStatus.EXPIRED
                    )
                )
            ),
            sendEvent = {}
        )
    }
}

@AppPreviews
@Composable
fun MyPlansScreenExpiredCancelledPreview() {
    FLODRestaurantTheme {
        MyPlansScreenContent(
            state = MyPlansState(
                selectedTab = PlanStatus.CANCELLED,
                cancelledPlans = listOf(
                    PlanItem(
                        id = "1",
                        imageUrl = "",
                        title = "VARIETY PACKAGE",
                        planDuration = "20 Days plan",
                        mealsCount = "x3 Meals",
                        startDate = "",
                        endDate = "",
                        isPickup = false,
                        status = PlanStatus.CANCELLED
                    )
                )
            ),
            sendEvent = {}
        )
    }
}

@AppPreviews
@Composable
fun MyPlansScreenEmptyPreview() {
    FLODRestaurantTheme {
        MyPlansScreenContent(
            state = MyPlansState(
                selectedTab = PlanStatus.ACTIVE,
                activePlans = emptyList()
            ),
            sendEvent = {}
        )
    }
}
