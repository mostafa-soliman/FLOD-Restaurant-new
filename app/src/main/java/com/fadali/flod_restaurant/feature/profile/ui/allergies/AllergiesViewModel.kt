package com.fadali.flod_restaurant.feature.profile.ui.allergies

import androidx.lifecycle.viewModelScope
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.profile.domain.model.UpdateUserAllergiesRequest
import com.fadali.flod_restaurant.feature.profile.domain.usecase.GetAllergiesLookupUseCase
import com.fadali.flod_restaurant.feature.profile.domain.usecase.UpdateUserAllergiesUseCase
import com.smyvo.core.network.NetworkException
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AllergiesViewModel(
    private val getAllergiesLookupUseCase: GetAllergiesLookupUseCase,
    private val updateUserAllergiesUseCase: UpdateUserAllergiesUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<AllergiesState, AllergiesEvent, AllergiesEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {

    private var searchJob: Job? = null

    override fun initialState(): AllergiesState = AllergiesState()

    override fun handleEvent(event: AllergiesEvent) {
        when (event) {
            AllergiesEvent.LoadAllergies -> loadAllergies()
            is AllergiesEvent.OnToggleAllergy -> toggleAllergy(event)
            AllergiesEvent.OnStartBtnClicked -> onStartBtnClicked()
            is AllergiesEvent.OnSearchQueryChange -> onSearchQueryChange(event)
        }
    }

    private fun loadAllergies(query: String? = null) {
        setState { copy(isLoading = true) }
        launchSafe {
            try {
                val allergies = getAllergiesLookupUseCase(query)
                setState {
                    copy(
                        isLoading = false,
                        allergies = allergies.items,
                    )
                }
            } catch (e: NetworkException) {
                val errorMessage = if (e.message.isNullOrEmpty()) ErrorDialogState.default.message
                else DeferredString.StringValue(e.message!!)

                sendEffect(
                    AllergiesEffect.ShowError(
                        dialogState = ErrorDialogState.default.copy(
                            message = errorMessage,
                            onPrimaryBtnClicked = { sendEvent(AllergiesEvent.LoadAllergies) }
                        )
                    )
                )
            }
        }
    }

    private fun onSearchQueryChange(event: AllergiesEvent.OnSearchQueryChange) {
        setState { copy(query = event.query) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            loadAllergies(event.query.takeIf { it.isNotBlank() })
        }
    }

    private fun toggleAllergy(event: AllergiesEvent.OnToggleAllergy) {
        val selectedAllergies = currentState.selectedAllergies.toMutableSet()
        if (selectedAllergies.contains(event.allergy)) {
            selectedAllergies.remove(event.allergy)
        } else {
            selectedAllergies.add(event.allergy)
        }
        setState { copy(selectedAllergies = selectedAllergies) }
    }

    private fun onStartBtnClicked() {
        setState { copy(isLoading = true) }
        launchSafe {
            try {
                val success = updateUserAllergiesUseCase(
                    request = UpdateUserAllergiesRequest(
                        allergyIds = currentState.selectedAllergies.toList().map { it.id }
                    )
                )
                if (success) {
                    sendEffect(AllergiesEffect.NavigateToHome)
                } else {
                    setState { copy(isLoading = false) }
                    sendEffect(
                        AllergiesEffect.ShowError(
                            dialogState = ErrorDialogState.default.copy(
                                onPrimaryBtnClicked = { sendEvent(AllergiesEvent.OnStartBtnClicked) },
                                secondaryButtonText = DeferredString.StringResource(R.string.cancel)
                            )
                        )
                    )
                }
            } catch (e: NetworkException) {
                setState { copy(isLoading = false) }
                val errorMessage = if (e.message.isNullOrEmpty()) ErrorDialogState.default.message
                else DeferredString.StringValue(e.message!!)

                sendEffect(
                    AllergiesEffect.ShowError(
                        dialogState = ErrorDialogState.default.copy(
                            message = errorMessage,
                            onPrimaryBtnClicked = { sendEvent(AllergiesEvent.OnStartBtnClicked) },
                            secondaryButtonText = DeferredString.StringResource(R.string.cancel)
                        )
                    )
                )
            }
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }
        val errorMessage = if (throwable.message.isNullOrEmpty()) ErrorDialogState.default.message
        else DeferredString.StringValue(throwable.message!!)

        sendEffect(
            AllergiesEffect.ShowError(
                dialogState = ErrorDialogState.default.copy(
                    message = errorMessage,
                    primaryButtonText = DeferredString.StringResource(R.string.ok),
                )
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}