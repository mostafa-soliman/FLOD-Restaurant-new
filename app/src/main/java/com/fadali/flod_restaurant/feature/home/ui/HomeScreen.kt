package com.fadali.flod_restaurant.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.feature.home.ui.components.FeatureHero
import com.fadali.flod_restaurant.feature.home.ui.components.HomeTopBar
import com.fadali.flod_restaurant.feature.home.ui.components.PlanCard
import com.fadali.flod_restaurant.feature.home.ui.model.ServicesUiModel
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.util.Dimens
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel(),
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(HomeEvent.LoadHomeData)
    }

    HandleState(viewModel.state) { state ->
        HomeScreenContent(
            state = state,
            sendEvent = { event -> viewModel.sendEvent(event) }
        )
    }

    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            is HomeEffect.NavigateToSelectPlan -> {
                navController.navigate(SubscriptionRoutes.SelectPlan)
            }

            is HomeEffect.ShowError -> {
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
private fun HomeScreenContent(
    state: HomeState,
    sendEvent: (HomeEvent) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AppLoadingDialog(isVisible = state.isLoading)

        LazyColumn(
            contentPadding = PaddingValues(
                start = Dimens.ScreenPadding,
                end = Dimens.ScreenPadding,
                top = Dimens.CardSpacing,
                bottom = Dimens.CardSpacing
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.CardSpacing)
        ) {
            item {
                HomeTopBar(userName = state.userName, avatarModel = state.avatarModel)
            }
            item {
                FeatureHero(
                    images = state.headerImages,
                    title = stringResource(id = R.string.hero_welcome)
                )
            }
            items(state.services) { service ->
                PlanCard(
                    imageUrl = service.imageModel,
                    title = stringResource(id = service.title),
                    ratingText = "⭐ 4.6 (9k)",
                    actionText = stringResource(id = service.actionText),
                    onActionClick = {
                        sendEvent(HomeEvent.OnActionButtonClicked(service.serviceId))
                    },
                    onCardClick = {
                        sendEvent(HomeEvent.OnServiceCardClicked(service.serviceId))
                    }
                )
            }
        }
    }
}

@AppPreviews
@Composable
private fun HomeScreenContentPreview() {
    FLODRestaurantTheme {
        HomeScreenContent(
            state = HomeState(
                isLoading = false,
                userName = "محمد",
                avatarModel = R.drawable.ic_avatar_img,
                headerImages = emptyList(),
                services = listOf(
                    ServicesUiModel(
                        imageModel = R.drawable.home_img3,
                        title = R.string.meal_diet,
                        actionText = R.string.select_plan,
                        serviceId = "1"
                    )
                )
            ),
            sendEvent = {}
        )
    }
}