package com.fadali.flod_restaurant.feature.profile.ui.allergies

import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.feature.profile.domain.model.AllergyData
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class AllergiesState(
    val isLoading: Boolean = false,
    val allergies: List<AllergyData> = emptyList(),
    val selectedAllergies: Set<AllergyData> = emptySet(),
    val query: String = "",
) : UiState

sealed interface AllergiesEvent : UiEvent {
    object LoadAllergies : AllergiesEvent
    data class OnToggleAllergy(val allergy: AllergyData) : AllergiesEvent
    data class OnSearchQueryChange(val query: String) : AllergiesEvent
    object OnStartBtnClicked : AllergiesEvent
}

sealed interface AllergiesEffect : UiEffect {
    object NavigateToHome : AllergiesEffect
    data class ShowError(val dialogState: ErrorDialogState?) : AllergiesEffect
}
