package com.fadali.flod_restaurant.feature.plan.ui.myplans

import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.feature.plan.ui.myplans.model.PlanItem
import com.fadali.flod_restaurant.feature.plan.ui.myplans.model.PlanStatus
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class MyPlansState(
    val isLoading: Boolean = false,
    val selectedTab: PlanStatus = PlanStatus.ACTIVE,
    val activePlans: List<PlanItem> = emptyList(),
    val expiredPlans: List<PlanItem> = emptyList(),
    val cancelledPlans: List<PlanItem> = emptyList(),
) : UiState

sealed interface MyPlansEvent : UiEvent {
    data object LoadPlans : MyPlansEvent
    data class OnTabSelected(val tab: PlanStatus) : MyPlansEvent
    data object OnBackClicked : MyPlansEvent
    data object OnSubscribeNowClicked : MyPlansEvent
}

sealed interface MyPlansEffect : UiEffect {
    data object NavigateBack : MyPlansEffect
    data object NavigateToSubscriptionPlans : MyPlansEffect
    data class ShowError(val dialogState: ErrorDialogState?) : MyPlansEffect
}
