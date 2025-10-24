package com.fadali.flod_restaurant.feature.subscription.ui.planmenu

import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class PlanMenuState(
    val isLoading: Boolean = false,
    val mealsByDay: Map<String, List<MealItemData>>? = null,
) : UiState

data class MealItemData(
    val id: String,
    val name: String,
    val imageUrl: String,
    val quantity: String,
    val price: Double
)

sealed class PlanMenuEvent : UiEvent {
    data class LoadData(val args: SubscriptionRoutes.PlanMenu) : PlanMenuEvent()
    data object OnBackPressed : PlanMenuEvent()
}

sealed class PlanMenuEffect : UiEffect {
    data object NavigateBack : PlanMenuEffect()
}
