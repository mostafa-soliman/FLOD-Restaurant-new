package com.fadali.flod_restaurant.feature.profile.ui.completeuserdata

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.profile.domain.model.CompleteUserDataRequest
import com.fadali.flod_restaurant.feature.profile.domain.usecase.CompleteUserDataUseCase
import com.fadali.flod_restaurant.feature.profile.domain.usecase.GetActivitiesLookupUseCase
import com.fadali.flod_restaurant.feature.profile.domain.usecase.GetGoalsLookupUseCase
import com.smyvo.core.network.NetworkException
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class CompleteUserDataViewModel(
    private val getActivitiesLookupUseCase: GetActivitiesLookupUseCase,
    private val getGoalsLookupUseCase: GetGoalsLookupUseCase,
    private val completeUserDataUseCase: CompleteUserDataUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<UserDataState, UserDataEvent, UserDataEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler,
) {
    override fun initialState(): UserDataState = UserDataState()

    override fun handleEvent(event: UserDataEvent) {
        when (event) {
            UserDataEvent.LoadLookups -> loadLookups()
            is UserDataEvent.OnWeightChange -> setState { copy(weight = event.weight) }
            is UserDataEvent.OnHeightChange -> setState { copy(height = event.height) }
            is UserDataEvent.OnActivityLevelSelected -> setState { copy(selectedActivity = event.activityLevel) }
            is UserDataEvent.OnGoalSelected -> setState { copy(selectedGoal = event.goal) }
            UserDataEvent.OnValidateHeight -> validateHeight()
            UserDataEvent.OnRemoveHeightError -> setState { copy(heightError = null) }
            UserDataEvent.OnValidateWeight -> validateWeight()
            UserDataEvent.OnRemoveWeightError -> setState { copy(weightError = null) }
            UserDataEvent.OnContinueClicked -> onContinueClicked()
        }
    }

    private fun loadLookups() {
        setState { copy(isLoading = true) }
        launchSafe {
            try {
                val activities = getActivitiesLookupUseCase()
                val goals = getGoalsLookupUseCase()
                setState {
                    copy(
                        isLoading = false,
                        activityLevels = activities.items,
                        goals = goals.items,
                    )
                }
            } catch (e: NetworkException) {
                val errorMessage = if (e.message.isNullOrEmpty()) ErrorDialogState.default.message
                else DeferredString.StringValue(e.message!!)

                sendEffect(
                    UserDataEffect.ShowError(
                        dialogState = ErrorDialogState.default.copy(
                            message = errorMessage,
                            onPrimaryBtnClicked = { sendEvent(UserDataEvent.LoadLookups) }
                        )
                    )
                )
            }
        }
    }

    private fun validateHeight() {
        if (currentState.height.isEmpty()) return
        try {
            val height = currentState.height.toDouble()
            if (height in currentState.minHeight.toDouble()..currentState.maxHeight.toDouble()) {
                setState { copy(heightError = null) }
            } else {
                setState { copy(heightError = DeferredString.StringResource(R.string.height_validation_error)) }
            }
        } catch (e: Exception) {
            setState { copy(heightError = DeferredString.StringResource(R.string.height_validation_error)) }
        }
    }

    private fun validateWeight() {
        if (currentState.weight.isEmpty()) return
        try {
            val height = currentState.weight.toDouble()
            if (height in currentState.minWeight.toDouble()..currentState.maxWeight.toDouble()) {
                setState { copy(weightError = null) }
            } else {
                setState { copy(weightError = DeferredString.StringResource(R.string.weight_validation_error)) }
            }
        } catch (e: Exception) {
            setState { copy(weightError = DeferredString.StringResource(R.string.weight_validation_error)) }
        }
    }

    private fun onContinueClicked() {
        validateHeight()
        validateWeight()
        if (currentState.heightError == null && currentState.weightError == null) {
            completeUserData()
        }
    }

    private fun completeUserData() {
        setState { copy(isLoading = true) }
        launchSafe {
            val height = if (currentState.height.isNotEmpty()) currentState.height.toDouble() else null
            val weight = if (currentState.weight.isNotEmpty()) currentState.weight.toDouble() else null
            val request = CompleteUserDataRequest(
                height = height,
                weight = weight,
                activityId = currentState.selectedActivity?.id,
                goalId = currentState.selectedGoal?.id
            )
            val isSuccess = completeUserDataUseCase(request = request)
            setState { copy(isLoading = false) }
            if (isSuccess) sendEffect(UserDataEffect.NavigateToAllergies)
            else sendEffect(UserDataEffect.ShowError())
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }
        val errorMessage = if (throwable.message.isNullOrEmpty()) ErrorDialogState.default.message
        else DeferredString.StringValue(throwable.message!!)

        sendEffect(
            UserDataEffect.ShowError(
                dialogState = ErrorDialogState.default.copy(
                    message = errorMessage,
                    primaryButtonText = DeferredString.StringResource(R.string.ok),
                )
            )
        )
    }

}
