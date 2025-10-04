package com.fadali.flod_restaurant.feature.profile.ui.completeuserdata

import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.profile.domain.model.ActivityData
import com.fadali.flod_restaurant.feature.profile.domain.model.GoalData
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class UserDataState(
    val isLoading: Boolean = false,
    val minHeight: Int = 100,
    val maxHeight: Int = 250,
    val minWeight: Int = 30,
    val maxWeight: Int = 150,
    val weight: String = "",
    val weightError: DeferredString? = null,
    val height: String = "",
    val heightError: DeferredString? = null,
    val activityLevels: List<ActivityData> = emptyList(),
    val goals: List<GoalData> = emptyList(),
    val selectedActivity: ActivityData? = null,
    val selectedGoal: GoalData? = null,
) : UiState

sealed interface UserDataEvent : UiEvent {
    data object LoadLookups : UserDataEvent
    data class OnWeightChange(val weight: String) : UserDataEvent
    data object OnValidateWeight : UserDataEvent
    data object OnRemoveWeightError : UserDataEvent
    data class OnHeightChange(val height: String) : UserDataEvent
    data object OnValidateHeight : UserDataEvent
    data object OnRemoveHeightError : UserDataEvent
    data class OnActivityLevelSelected(val activityLevel: ActivityData) : UserDataEvent
    data class OnGoalSelected(val goal: GoalData) : UserDataEvent
    object OnContinueClicked : UserDataEvent
}

sealed interface UserDataEffect : UiEffect {
    object NavigateToAllergies : UserDataEffect
    data class ShowError(val dialogState: ErrorDialogState? = null) : UserDataEffect
}
