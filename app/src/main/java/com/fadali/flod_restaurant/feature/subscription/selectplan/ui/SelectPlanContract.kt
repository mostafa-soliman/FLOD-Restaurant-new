package com.fadali.flod_restaurant.feature.subscription.selectplan.ui

import androidx.annotation.StringRes
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState

data class SelectPlanState(
    val isLoading: Boolean = false,
    val packageType: PackageType = PackageType.PICKUP,
    val categories: List<ProductCategory> = emptyList(),
    val selectedCategory: ProductCategory = ProductCategory.empty,
    val customerAddresses: List<CustomerAddress> = emptyList(),
    val selectedCustomerAddress: CustomerAddress = CustomerAddress.empty,
    val branches: List<Branch> = emptyList(),
    val selectedBranch: Branch = Branch.empty.copy(displayName = "No branch selected"),
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
    data class OnSubscriptionTypeChanged(val type: PackageType) : SelectPlanEvent
    data class OnCategorySelected(val category: ProductCategory) : SelectPlanEvent
    data class OnDurationSelected(val duration: Int) : SelectPlanEvent
    data class OnMealsCountSelected(val numberOfMeals: Int) : SelectPlanEvent
}

sealed interface SelectPlanEffect : UiEffect {
    data object NavigateBack : SelectPlanEffect
    data object NavigateToNextScreen : SelectPlanEffect
    data class ShowError(val dialogState: ErrorDialogState?) : SelectPlanEffect
}
