package com.fadali.flod_restaurant.feature.subscription.ui.review

import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.feature.subscription.domain.model.promocode.PromoCode
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.AddonUiModel
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.ConfirmationInfo
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.PromoCodeValidationState
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState
import java.math.BigDecimal

data class SubscriptionReviewState(
    val isLoading: Boolean = false,
    val addOns: List<AddonUiModel>? = null,
    val promoCodeText: String = "",
    val appliedPromoCode: PromoCode? = null,
    val promoCodeValidationState: PromoCodeValidationState = PromoCodeValidationState.NONE,
    val isTermsAccepted: Boolean = false,
    val args: SubscriptionRoutes.SubscriptionReview? = null,
    val confirmationInfo: ConfirmationInfo = ConfirmationInfo(),
    val createdSubscriptionId: String? = null,
    val paymentUrl: String? = null,
) : UiState {
    val planPrice: BigDecimal
        get() = (args?.packageDetails?.price ?: 0.0).toBigDecimal()

    val totalAddonPrices: BigDecimal
        get() = addOns?.sumOf { it.price.toBigDecimal().multiply(it.quantity.toBigDecimal()) } ?: BigDecimal.ZERO

    val promoCodeDiscount: BigDecimal?
        get() = appliedPromoCode?.discountValue?.toBigDecimal()

    val totalPrice: BigDecimal
        get() = if (promoCodeDiscount != null) {
            val discountedTotal = (planPrice + totalAddonPrices) - promoCodeDiscount!!
            if (discountedTotal < BigDecimal.ZERO) BigDecimal.ZERO else discountedTotal
        } else {
            planPrice + totalAddonPrices
        }
}

sealed interface SubscriptionReviewEvent : UiEvent {
    data class LoadData(val args: SubscriptionRoutes.SubscriptionReview) : SubscriptionReviewEvent
    data class OnAddOnQuantityChange(val index: Int, val quantity: Int) : SubscriptionReviewEvent
    data class OnPromoCodeChange(val promoCode: String) : SubscriptionReviewEvent
    object OnApplyPromoCode : SubscriptionReviewEvent
    data class OnTermsToggled(val accepted: Boolean) : SubscriptionReviewEvent
    object OnConfirmClicked : SubscriptionReviewEvent
    object OnBackClicked : SubscriptionReviewEvent
    object OnNavigateToTermsAndConditions : SubscriptionReviewEvent
}

sealed interface SubscriptionReviewEffect : UiEffect {
    object NavigateBack : SubscriptionReviewEffect
    data class NavigateToNextScreen(val route: SubscriptionRoutes) : SubscriptionReviewEffect
    data class ShowError(val dialogState: ErrorDialogState? = null) : SubscriptionReviewEffect
}
