package com.fadali.flod_restaurant.feature.home.ui

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.feature.home.ui.model.ServicesUiModel
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class HomeState(
    val isLoading: Boolean = false,
    val services: List<ServicesUiModel> = emptyList(),
    val userName: String = "Ahmed",
    val avatarModel: Any = R.drawable.ic_avatar_img,
    val headerImages: List<Int> = emptyList()
) : UiState

sealed interface HomeEvent : UiEvent {
    data object LoadHomeData : HomeEvent
    data class OnServiceCardClicked(val planId: String) : HomeEvent
    data class OnActionButtonClicked(val planId: String) : HomeEvent
}

sealed interface HomeEffect : UiEffect {
    data class NavigateToSelectPlan(val planId: String) : HomeEffect
    data class ShowError(val dialogState: ErrorDialogState?) : HomeEffect
}
