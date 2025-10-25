package com.fadali.flod_restaurant.feature.subscription.ui.terms

import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetTermsAndConditionsUseCase
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class SubscriptionTermsAndConditionsViewModel(
    private val getTermsAndConditionsUseCase: GetTermsAndConditionsUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<SubscriptionTermsAndConditionsState, SubscriptionTermsAndConditionsUiEvent, SubscriptionTermsAndConditionsUiEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {
    override fun initialState(): SubscriptionTermsAndConditionsState =
        SubscriptionTermsAndConditionsState()

    override fun handleEvent(event: SubscriptionTermsAndConditionsUiEvent) {
        when (event) {
            is SubscriptionTermsAndConditionsUiEvent.LoadData -> loadTermsAndConditions(event.args)
            is SubscriptionTermsAndConditionsUiEvent.OnScrollPositionChanged -> onScrollPositionChanged(
                event
            )

            SubscriptionTermsAndConditionsUiEvent.OnAcceptButtonClicked -> {
                currentState.args?.let { args ->
                    sendEffect(
                        SubscriptionTermsAndConditionsUiEffect.NavigateToPayment(
                            SubscriptionRoutes.Payment(
                                paymentUrl = args.paymentUrl,
                                subscriptionId = args.subscriptionId
                            )
                        )
                    )
                }
            }

            SubscriptionTermsAndConditionsUiEvent.OnBackPressed -> {
                sendEffect(SubscriptionTermsAndConditionsUiEffect.NavigateBack)
            }
        }
    }

    private fun loadTermsAndConditions(args: SubscriptionRoutes.TermsAndConditions) {
        setState {
            copy(
                isLoading = true,
                isAcceptButtonEnabled = false,
                hasReachedEnd = false
            )
        }
        launchSafe {
            val remoteText = try {
                getTermsAndConditionsUseCase()
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
            setState {
                copy(
                    termsAndConditionsText = remoteText,
                    args = args,
                    isLoading = false
                )
            }
        }
    }

    private fun onScrollPositionChanged(event: SubscriptionTermsAndConditionsUiEvent.OnScrollPositionChanged) {
        val hasReachedEnd = if (!currentState.hasReachedEnd) event.hasReachedEnd else true

        setState {
            copy(
                hasReachedEnd = hasReachedEnd,
                isAcceptButtonEnabled = hasReachedEnd
            )
        }
    }

}
