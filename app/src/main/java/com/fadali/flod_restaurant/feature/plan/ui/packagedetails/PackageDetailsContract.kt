package com.fadali.flod_restaurant.feature.plan.ui.packagedetails

import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.feature.plan.ui.model.AddOnUiModel
import com.fadali.flod_restaurant.feature.plan.ui.model.MenuItemUiModel
import com.fadali.flod_restaurant.feature.plan.ui.model.PackageDayUiModel
import com.fadali.flod_restaurant.feature.plan.ui.model.PackageInfoUiModel
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class PackageDetailsState(
    val isLoading: Boolean = false,
    val subscriptionId: String? = null,
    val packageInfo: PackageInfoUiModel? = null,
    val packageDays: List<PackageDayUiModel> = emptyList(),
    val selectedDayIndex: Int = 0,
    val todayMenuItems: List<MenuItemUiModel> = emptyList(),
    val addOns: List<AddOnUiModel> = emptyList()
) : UiState

sealed interface PackageDetailsEvent : UiEvent {
    data class Initialize(val subscriptionId: String?) : PackageDetailsEvent
    object LoadPackageDetails : PackageDetailsEvent
    data class Load(val subscriptionId: String) : PackageDetailsEvent
    data class OnDaySelected(val index: Int) : PackageDetailsEvent
    data class OnCustomizeMenuClick(val menuItemId: String) : PackageDetailsEvent
    data class OnFreezePackageClick(val subscriptionId: String) : PackageDetailsEvent
    data class OnAddOnQuantityChange(val addOnId: String, val quantity: Int) : PackageDetailsEvent
    object OnBackClick : PackageDetailsEvent
}

sealed interface PackageDetailsEffect : UiEffect {
    object NavigateBack : PackageDetailsEffect
    data class ShowError(val dialogState: ErrorDialogState?) : PackageDetailsEffect
    data class NavigateToCustomize(val menuItemId: String) : PackageDetailsEffect
}
