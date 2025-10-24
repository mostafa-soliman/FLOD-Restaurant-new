package com.fadali.flod_restaurant.feature.subscription.ui.viewpackage

import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageMeal
import com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.model.MealItem
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class ViewPackageState(
    val isLoading: Boolean = false,
    val args: SubscriptionRoutes.ViewPackage? = null,
    val packageMeals: List<PackageMeal>? = null,
    val mealItems: List<MealItem> = emptyList(),
) : UiState

sealed class ViewPackageEvent : UiEvent {
    data class LoadData(val args: SubscriptionRoutes.ViewPackage) : ViewPackageEvent()
    data object OnBackPressed : ViewPackageEvent()
    data object OnSelectPlanClicked : ViewPackageEvent()
    data object OnViewPlanMenuClicked : ViewPackageEvent()
}

sealed class ViewPackageEffect : UiEffect {
    data object NavigateBack : ViewPackageEffect()
    data class NavigateToNextScreen(val route: SubscriptionRoutes) : ViewPackageEffect()
    data class ShowError(val dialogState: ErrorDialogState?) : ViewPackageEffect()
}
