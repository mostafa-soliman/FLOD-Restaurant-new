package com.fadali.flod_restaurant.feature.subscription.ui.viewpackage

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
import com.fadali.flod_restaurant.core.component.button.AppSecondaryButton
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.component.image.AppNetworkImage
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.topBar.AppTopBar
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.components.MealPlansSection
import com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.components.NutritionInfoSection
import com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.components.PackageHeaderSection
import com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.model.MealItem
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ViewPackageScreen(
    navController: NavController,
    viewModel: ViewPackageViewModel = koinViewModel(),
    args: SubscriptionRoutes.ViewPackage
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(ViewPackageEvent.LoadData(args))
    }
    HandleState(viewModel.state) { state ->
        ViewPackageScreenContent(
            state = state,
            sendEvent = viewModel::handleEvent
        )
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            ViewPackageEffect.NavigateBack -> navController.navigateBack()
            is ViewPackageEffect.NavigateToNextScreen -> {
                navController.navigate(effect.route)
            }
            is ViewPackageEffect.ShowError -> {
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
private fun ViewPackageScreenContent(
    state: ViewPackageState,
    sendEvent: (ViewPackageEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AppLoadingDialog(state.isLoading)

        AppNetworkImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .align(Alignment.TopCenter),
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
            title = stringResource(R.string.details),
            onBackClick = { sendEvent(ViewPackageEvent.OnBackPressed) },
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
            PackageHeaderSection(
                packageTitle = state.args?.packageDetails?.title ?: "",
                packageSubtitle = state.args?.packageDetails?.description ?: "",
                packagePrice = state.args?.packageDetails?.price?.toFloat() ?: 0f
            )
            NutritionInfoSection(
                calories = state.args?.packageDetails?.nutritionInfo?.calories?.toString() ?: "",
                protein = state.args?.packageDetails?.nutritionInfo?.protein?.toString() ?: "",
                carbs = state.args?.packageDetails?.nutritionInfo?.carbohydrates?.toString() ?: "",
                fat = state.args?.packageDetails?.nutritionInfo?.fats?.toString() ?: ""
            )
            MealPlansSection(
                mealItems = state.mealItems
            )
            Spacer(Modifier.weight(1f))
            AppPrimaryButton(
                text = stringResource(R.string.callToAction_select_plan),
                onClick = { sendEvent(ViewPackageEvent.OnSelectPlanClicked) },
            )
            AppSecondaryButton(
                modifier = Modifier.padding(vertical = 16.dp),
                text = stringResource(R.string.view_plan_menu),
                onClick = { sendEvent(ViewPackageEvent.OnViewPlanMenuClicked) },
            )
        }
    }
}

@AppPreviews
@Composable
private fun ViewPackageScreenContentPreview() {
    val mockState = ViewPackageState(
        mealItems = listOf(
            MealItem(
                imageUrl = "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?w=1200",
                name = "Diet",
                quantity = "x12"
            ),
            MealItem(
                imageUrl = "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=1200",
                name = "Avocado Salad",
                quantity = "x12"
            )
        )
    )

    FLODRestaurantTheme {
        ViewPackageScreenContent(
            state = mockState,
            sendEvent = {}
        )
    }
}
