package com.fadali.flod_restaurant.feature.subscription.ui.payment

import com.fadali.flod_restaurant.core.component.result.bottomsheet.BottomSheetResult
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class PaymentState(
    val isLoading: Boolean = false,
    val paymentUrl: String = "",
    val showWebView: Boolean = false,
) : UiState

sealed interface PaymentEvent : UiEvent {
    data class LoadPaymentUrl(val paymentUrl: String) : PaymentEvent
    data class OnUrlChanged(val url: String) : PaymentEvent
    object OnPageStartedLoading : PaymentEvent
    object OnPageFinishedLoading : PaymentEvent
    object OnPageLoadError : PaymentEvent
    object OnBackPressed : PaymentEvent
}

sealed interface PaymentEffect : UiEffect {
    data class ShowResultSheet(val result: BottomSheetResult) : PaymentEffect
    object NavigateBack : PaymentEffect
    object NavigateToNextScreen : PaymentEffect
}
