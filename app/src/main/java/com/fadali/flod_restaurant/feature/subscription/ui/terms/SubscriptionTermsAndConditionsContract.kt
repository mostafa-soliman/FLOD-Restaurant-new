package com.fadali.flod_restaurant.feature.subscription.ui.terms

import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class SubscriptionTermsAndConditionsState(
    val isLoading: Boolean = false,
    val termsAndConditionsText: String = "",
    val hasReachedEnd: Boolean = false,
    val isAcceptButtonEnabled: Boolean = false,
    val args: SubscriptionRoutes.TermsAndConditions? = null
) : UiState

sealed class SubscriptionTermsAndConditionsUiEvent : UiEvent {
    data class LoadData(val args: SubscriptionRoutes.TermsAndConditions) : SubscriptionTermsAndConditionsUiEvent()
    data class OnScrollPositionChanged(val hasReachedEnd: Boolean) : SubscriptionTermsAndConditionsUiEvent()
    data object OnAcceptButtonClicked : SubscriptionTermsAndConditionsUiEvent()
    data object OnBackPressed : SubscriptionTermsAndConditionsUiEvent()
}

sealed class SubscriptionTermsAndConditionsUiEffect : UiEffect {
    data class NavigateToPayment(val route: SubscriptionRoutes.Payment) : SubscriptionTermsAndConditionsUiEffect()
    data object NavigateBack : SubscriptionTermsAndConditionsUiEffect()
}
