package com.fadali.flod_restaurant.feature.plan.ui.myplans

import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.network.appconfig.PaymentMethod
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.plan.domain.model.GetSubscriptionListRequest
import com.fadali.flod_restaurant.feature.plan.domain.usecase.GetSubscriptionListUseCase
import com.fadali.flod_restaurant.feature.plan.ui.myplans.mapper.toPlanItem
import com.smyvo.core.network.NetworkException
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class MyPlansViewModel(
    private val getSubscriptionListUseCase: GetSubscriptionListUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<MyPlansState, MyPlansEvent, MyPlansEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {

    override fun initialState(): MyPlansState = MyPlansState()

    override fun handleEvent(event: MyPlansEvent) {
        when (event) {
            MyPlansEvent.LoadPlans -> loadPlans()
            is MyPlansEvent.OnTabSelected -> onTabSelected(event)
            MyPlansEvent.OnBackClicked -> sendEffect(MyPlansEffect.NavigateBack)
            MyPlansEvent.OnSubscribeNowClicked -> sendEffect(MyPlansEffect.NavigateToSelectPlan)
        }
    }

    private fun loadPlans() {
        setState { copy(isLoading = true) }
        launchSafe {
            try {
                // Fetch active plans (SubscriptionStatus = 1)
                val activeRequest = GetSubscriptionListRequest(
                    subscriptionStatus = 1, // Active
                    paymentMethod = PaymentMethod.DIGITAL_WALLET.value,
                    packageId = null,
                    skip = 0,
                    rowCount = 100
                )
                val activeResponse = getSubscriptionListUseCase(activeRequest)
                val activePlans = activeResponse.items.map { it.toPlanItem() }

                // Fetch expired plans (SubscriptionStatus = 2)
                val expiredRequest = GetSubscriptionListRequest(
                    subscriptionStatus = 2, // Expired
                    paymentMethod = PaymentMethod.DIGITAL_WALLET.value,
                    packageId = null,
                    skip = 0,
                    rowCount = 100
                )
                val expiredResponse = getSubscriptionListUseCase(expiredRequest)
                val expiredPlans = expiredResponse.items.map { it.toPlanItem() }

                // Fetch cancelled plans (SubscriptionStatus = 3)
                val cancelledRequest = GetSubscriptionListRequest(
                    subscriptionStatus = 3, // Cancelled
                    paymentMethod = PaymentMethod.DIGITAL_WALLET.value,
                    packageId = null,
                    skip = 0,
                    rowCount = 100
                )
                val cancelledResponse = getSubscriptionListUseCase(cancelledRequest)
                val cancelledPlans = cancelledResponse.items.map { it.toPlanItem() }

                setState {
                    copy(
                        isLoading = false,
                        activePlans = activePlans,
                        expiredPlans = expiredPlans,
                        cancelledPlans = cancelledPlans
                    )
                }
            } catch (e: NetworkException) {
                setState { copy(isLoading = false) }
                val errorMessage = if (e.message.isNullOrEmpty()) ErrorDialogState.default.message
                else DeferredString.StringValue(e.message!!)

                sendEffect(
                    MyPlansEffect.ShowError(
                        dialogState = ErrorDialogState.default.copy(
                            message = errorMessage,
                            onPrimaryBtnClicked = { sendEvent(MyPlansEvent.LoadPlans) }
                        )
                    )
                )
            }
        }
    }

    private fun onTabSelected(event: MyPlansEvent.OnTabSelected) {
        setState { copy(selectedTab = event.tab) }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }
        val errorMessage = if (throwable.message.isNullOrEmpty()) ErrorDialogState.default.message
        else DeferredString.StringValue(throwable.message!!)

        sendEffect(
            MyPlansEffect.ShowError(
                dialogState = ErrorDialogState.default.copy(
                    message = errorMessage,
                )
            )
        )
    }
}
