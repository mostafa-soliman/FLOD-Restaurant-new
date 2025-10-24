package com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui

import androidx.annotation.StringRes
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails
import com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui.utils.BranchAddressResult
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class SelectPlanState(
    val isLoading: Boolean = false,
    val packageType: PackageType = PackageType.PICKUP,
    val categories: List<ProductCategory> = emptyList(),
    val selectedCategory: ProductCategory? = null,
    val customerAddresses: List<CustomerAddress> = emptyList(),
    val selectedDeliveryAddress: CustomerAddress? = null,
    val branches: List<Branch> = emptyList(),
    val selectedBranch: Branch? = null,
    val packages: List<PackageDetails> = emptyList(),
    val packageDurationList: List<Int> = emptyList(),
    val selectedPackageDuration: Int = -1,
    val numberOfMealsList: List<Int> = emptyList(),
    val selectedNumberOfMeals: Int = -1,
    val filteredPackages: List<PackageDetails> = emptyList(),
    @StringRes val branchTitle: Int = R.string.pickup_branch,
    @StringRes val categoriesTitle: Int = R.string.plan_categories,
) : UiState

sealed interface SelectPlanEvent : UiEvent {
    data object LoadData : SelectPlanEvent
    data object OnBackBtnClicked : SelectPlanEvent
    data class ProcessBranchResult(val result: BranchAddressResult) : SelectPlanEvent
    data class OnSubscriptionTypeChanged(val type: PackageType) : SelectPlanEvent
    data class OnCategorySelected(val category: ProductCategory) : SelectPlanEvent
    data class OnDurationSelected(val duration: Int) : SelectPlanEvent
    data class OnMealsCountSelected(val numberOfMeals: Int) : SelectPlanEvent
    data object OnEditAddressClicked : SelectPlanEvent
    data class OnPackageSelected(val packageDetails: PackageDetails) : SelectPlanEvent
    data class OnPackageCardClicked(val packageDetails: PackageDetails) : SelectPlanEvent
}

sealed interface SelectPlanEffect : UiEffect {
    data object NavigateBack : SelectPlanEffect
    data class NavigateToNextScreen(val route: SubscriptionRoutes) : SelectPlanEffect
    data class ShowError(val dialogState: ErrorDialogState?) : SelectPlanEffect
}
