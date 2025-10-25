package com.fadali.flod_restaurant.feature.subscription.ui.payment

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.result.bottomsheet.BottomSheetResult
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.subscription.ui.payment.utils.SubscriptionPaymentConstants.REDIRECT_URL_CANCELLED_KEYWORD
import com.fadali.flod_restaurant.feature.subscription.ui.payment.utils.SubscriptionPaymentConstants.REDIRECT_URL_KEYWORD
import com.fadali.flod_restaurant.feature.subscription.ui.payment.utils.SubscriptionPaymentConstants.REDIRECT_URL_SUCCESS_KEYWORD
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class PaymentViewModel(
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<PaymentState, PaymentEvent, PaymentEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {

    override fun initialState(): PaymentState = PaymentState()

    override fun handleEvent(event: PaymentEvent) {
        when (event) {
            is PaymentEvent.LoadPaymentUrl -> loadPaymentUrl(event.paymentUrl, event.subscriptionId)
            is PaymentEvent.OnUrlChanged -> handleUrlChange(event.url)
            PaymentEvent.OnPageStartedLoading -> onPageStartedLoading()
            PaymentEvent.OnPageFinishedLoading -> onPageFinishedLoading()
            PaymentEvent.OnPageLoadError -> onPageLoadError()
            PaymentEvent.OnBackPressed -> sendEffect(PaymentEffect.NavigateBack)
        }
    }

    private fun loadPaymentUrl(paymentUrl: String, subscriptionId: String) {
        val isValidUrl = paymentUrl.isNotEmpty()
        if (isValidUrl) {
            setState {
                copy(
                    paymentUrl = paymentUrl,
                    subscriptionId = subscriptionId,
                    isLoading = true,
                    showWebView = true,
                )
            }
        } else {
            sendEffect(
                PaymentEffect.ShowResultSheet(
                    result = BottomSheetResult.Failure(
                        message = DeferredString.StringResource(R.string.something_went_wrong),
                        primaryActionText = DeferredString.StringResource(R.string.try_again),
                        onPrimaryActionClicked = { sendEvent(PaymentEvent.OnBackPressed) },
                    )
                )
            )
        }
    }

    private fun handleUrlChange(url: String) {
        if (url.contains(REDIRECT_URL_KEYWORD)) {

            val isSuccess = url.contains(REDIRECT_URL_SUCCESS_KEYWORD)
            val isCancelled = url.contains(REDIRECT_URL_CANCELLED_KEYWORD)

            setState {
                copy(
                    showWebView = false,
                    isLoading = false
                )
            }
            if (isSuccess) {
                sendEffect(
                    PaymentEffect.ShowResultSheet(
                        result = BottomSheetResult.Success(
                            message = DeferredString.StringResource(R.string.view_package_sheet_text),
                            primaryActionText = DeferredString.StringResource(R.string.view_package_button),
                            onPrimaryActionClicked = {
                                sendEffect(PaymentEffect.NavigateToPackageDetails(currentState.subscriptionId))
                            },
                        )
                    )
                )
            } else if (isCancelled) {
                sendEffect(
                    PaymentEffect.ShowResultSheet(
                        result = BottomSheetResult.Failure(
                            message = DeferredString.StringResource(R.string.payment_cancelled),
                            primaryActionText = DeferredString.StringResource(R.string.ok),
                            onPrimaryActionClicked = { sendEffect(PaymentEffect.NavigateBack) },
                        )
                    )
                )
            }
        }
    }

    private fun onPageStartedLoading() {
        setState { copy(isLoading = true) }
    }

    private fun onPageFinishedLoading() {
        setState { copy(isLoading = false) }
    }

    private fun onPageLoadError() {
        setState {
            copy(
                isLoading = false,
                showWebView = false,
            )
        }
        sendEffect(
            PaymentEffect.ShowResultSheet(
                result = BottomSheetResult.Failure(
                    message = DeferredString.StringResource(R.string.something_went_wrong),
                    primaryActionText = DeferredString.StringResource(R.string.try_again),
                    onPrimaryActionClicked = { sendEffect(PaymentEffect.NavigateBack) },
                )
            )
        )
    }
}
