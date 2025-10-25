package com.fadali.flod_restaurant.feature.plan.ui.myplans

import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.plan.domain.model.PlanItem
import com.fadali.flod_restaurant.feature.plan.domain.model.PlanStatus
import com.smyvo.core.network.NetworkException
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class MyPlansViewModel(
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
            MyPlansEvent.OnSubscribeNowClicked -> sendEffect(MyPlansEffect.NavigateToSubscriptionPlans)
        }
    }

    private fun loadPlans() {
        setState { copy(isLoading = true) }
        launchSafe {
            try {
                // TODO: Replace with actual API call
                // For now, using mock data
                val mockActivePlans = listOf(
                    PlanItem(
                        id = "1",
                        imageUrl = "",
                        title = "Donuts",
                        planDuration = "20 Days plan",
                        mealsCount = "x3 Meals",
                        startDate = "Start: 01 May",
                        endDate = "15 May",
                        isPickup = false,
                        status = PlanStatus.ACTIVE
                    ),
                    PlanItem(
                        id = "2",
                        imageUrl = "",
                        title = "Molten cake",
                        planDuration = "20 Days plan",
                        mealsCount = "x3 Meals",
                        startDate = "Start: 01 May",
                        endDate = "15 May",
                        isPickup = true,
                        status = PlanStatus.ACTIVE
                    ),
                    PlanItem(
                        id = "3",
                        imageUrl = "",
                        title = "cheese cake",
                        planDuration = "20 Days plan",
                        mealsCount = "x3 Meals",
                        startDate = "Start: 01 May",
                        endDate = "15 May",
                        isPickup = false,
                        status = PlanStatus.ACTIVE
                    )
                )

                val mockExpiredPlans = listOf(
                    PlanItem(
                        id = "4",
                        imageUrl = "",
                        title = "VARIETY PACKAGE",
                        planDuration = "20 Days plan",
                        mealsCount = "x3 Meals",
                        startDate = "",
                        endDate = "",
                        isPickup = false,
                        status = PlanStatus.EXPIRED
                    ),
                    PlanItem(
                        id = "5",
                        imageUrl = "",
                        title = "PREMIUM PACKAGE",
                        planDuration = "20 Days plan",
                        mealsCount = "x3 Meals",
                        startDate = "",
                        endDate = "",
                        isPickup = true,
                        status = PlanStatus.EXPIRED
                    )
                )

                // Empty list for cancelled plans
                val mockCancelledPlans = listOf(
                    PlanItem(
                        id = "4",
                        imageUrl = "",
                        title = "VARIETY PACKAGE1",
                        planDuration = "20 Days plan",
                        mealsCount = "1x Meals",
                        startDate = "",
                        endDate = "",
                        isPickup = false,
                        status = PlanStatus.CANCELLED
                    ),
                    PlanItem(
                        id = "5",
                        imageUrl = "",
                        title = "PREMIUM PACKAGE",
                        planDuration = "10 Days plan",
                        mealsCount = "2x Meals",
                        startDate = "",
                        endDate = "",
                        isPickup = true,
                        status = PlanStatus.CANCELLED
                    )
                )

                setState {
                    copy(
                        isLoading = false,
                        activePlans = mockActivePlans,
                        expiredPlans = mockExpiredPlans,
                        cancelledPlans = mockCancelledPlans
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
