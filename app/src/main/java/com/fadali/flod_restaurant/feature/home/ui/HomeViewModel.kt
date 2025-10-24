package com.fadali.flod_restaurant.feature.home.ui

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.home.ui.model.ServicesUiModel
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class HomeViewModel(
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<HomeState, HomeEvent, HomeEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {

    override fun initialState(): HomeState = HomeState()

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadHomeData -> loadHomeData()
            is HomeEvent.OnServiceCardClicked -> handlePlanSelection(event.planId)
            is HomeEvent.OnActionButtonClicked -> handlePlanSelection(event.planId)
        }
    }

    private fun loadHomeData() {
        setState { copy(isLoading = true) }

        try {
            val headerImages = listOf(
                R.drawable.home_img1,
                R.drawable.home_img2
            )

            val services = listOf(
                ServicesUiModel(
                    imageModel = R.drawable.home_img3,
                    title = R.string.meal_diet,
                    actionText = R.string.callToAction_select_plan,
                    serviceId = "diet"
                )
            )

            setState {
                copy(
                    isLoading = false,
                    services = services,
                    headerImages = headerImages
                )
            }
        } catch (e: Exception) {
            setState { copy(isLoading = false) }
            handleException(e)
        }
    }


    private fun handlePlanSelection(planId: String) {
        val selectedMeal = currentState.services.find { meal -> meal.serviceId == planId }

        if (selectedMeal?.actionText == R.string.callToAction_select_plan) {
            sendEffect(HomeEffect.NavigateToSelectPlan(planId))
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }

        val errorMessage = if (throwable.message.isNullOrEmpty()) {
            ErrorDialogState.default.message
        } else {
            DeferredString.StringValue(throwable.message!!)
        }

        sendEffect(
            HomeEffect.ShowError(
                dialogState = ErrorDialogState.default.copy(
                    message = errorMessage,
                    primaryButtonText = DeferredString.StringResource(R.string.ok),
                )
            )
        )
    }
}