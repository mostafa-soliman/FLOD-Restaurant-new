package com.fadali.flod_restaurant.feature.subscription.selectplan.ui

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.core.ui.exception.UiException
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.GetPackagesRequest
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetBranchesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetCustomerAddressesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetPackagesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetProductCategoriesUseCase
import com.fadali.flod_restaurant.feature.subscription.selectplan.ui.mockdata.SubscriptionMockData
import com.smyvo.core.network.NetworkException
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SelectPlanViewModel(
    private val getCustomerAddressesUseCase: GetCustomerAddressesUseCase,
    private val getBranchesUseCase: GetBranchesUseCase,
    private val getProductCategoriesUseCase: GetProductCategoriesUseCase,
    private val getPackagesUseCase: GetPackagesUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<SelectPlanState, SelectPlanEvent, SelectPlanEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {
    override fun initialState(): SelectPlanState = SelectPlanState()

    override fun handleEvent(event: SelectPlanEvent) {
        when (event) {
            SelectPlanEvent.LoadData -> loadData()
            SelectPlanEvent.OnBackBtnClicked -> sendEffect(SelectPlanEffect.NavigateBack)
            is SelectPlanEvent.OnSubscriptionTypeChanged -> onSubscriptionTypeChanged(event.type)
            is SelectPlanEvent.OnCategorySelected -> onCategorySelected(event)
            is SelectPlanEvent.OnDurationSelected -> onDurationSelected(event)
            is SelectPlanEvent.OnMealsCountSelected -> onMealsCountSelected(event)
        }
    }

    private fun loadData() {
        launchSafe {
            setState { copy(isLoading = true) }
            try {
                coroutineScope {
                    launch { loadCustomerAddresses() }
                    launch { loadBranches() }
                    launch { loadProductCategories() }
                }
                loadPackages()
            } catch (e: NetworkException) {
                val errorMessage = if (e.message.isNullOrEmpty()) ErrorDialogState.default.message
                else DeferredString.StringValue(e.message!!)
                throw UiException(
                    dialogState = ErrorDialogState.default.copy(
                        message = errorMessage,
                        onPrimaryBtnClicked = { sendEvent(SelectPlanEvent.LoadData) },
                        secondaryButtonText = DeferredString.StringResource(R.string.cancel)
                    )
                )
            }
            setState { copy(isLoading = false) }
        }
    }

    private suspend fun loadCustomerAddresses() {
//        val addresses = getCustomerAddressesUseCase()
//        setState {
//            copy(
//                customerAddresses = addresses,
//                selectedCustomerAddress = if (addresses.isNotEmpty()) addresses.first() else selectedCustomerAddress
//            )
//        }
    }

    private suspend fun loadBranches() {
        val branches = getBranchesUseCase().ifEmpty { // TODO: Remove this mock data)
            SubscriptionMockData.branches
        }
        setState {
            copy(
                branches = branches,
                selectedBranch = if (branches.isNotEmpty()) branches.first() else selectedBranch
            )
        }
    }

    private suspend fun loadProductCategories() {
        val categories = getProductCategoriesUseCase().ifEmpty { // TODO: Remove this mock data
            SubscriptionMockData.categories
        }
        setState {
            copy(
                categories = categories,
                selectedCategory = if (categories.isNotEmpty()) categories.first() else selectedCategory
            )
        }
    }

    private suspend fun loadPackages() {
        val request = GetPackagesRequest( // Fixme: The selected data can be empty
            packageType = currentState.packageType.value,
            productCategoryId = /* currentState.selectedCategory.id */ "04388a04-ccdf-b896-2941-dca8fdbc5f79",
//            customerAddressId = currentState.selectedCustomerAddress.id,
//            branchId = currentState.selectedBranch.id,
        )
        val packages = getPackagesUseCase(request).ifEmpty { // TODO: Remove this mock data
            SubscriptionMockData.packages
        }
        setState { copy(packages = packages) }
        groupDurationsAndMealsCount()
        filterPackages()
    }

    private fun groupDurationsAndMealsCount() {
        setState { copy(isLoading = true) }
        val durationList = currentState.packages.map { it.durationDays }.distinct().sorted()
        val numberOfMealsList = currentState.packages.map { it.mealsPerDay }.distinct().sorted()
        val selectedPackageDuration = if (currentState.selectedPackageDuration == -1 && durationList.isNotEmpty()) {
            durationList.first()
        } else {
            currentState.selectedPackageDuration
        }
        val selectedNumberOfMeals = if (currentState.selectedNumberOfMeals == -1 && numberOfMealsList.isNotEmpty()) {
            numberOfMealsList.first()
        } else {
            currentState.selectedNumberOfMeals
        }
        setState {
            copy(
                isLoading = false,
                packageDurationList = durationList,
                selectedPackageDuration = selectedPackageDuration,
                numberOfMealsList = numberOfMealsList,
                selectedNumberOfMeals = selectedNumberOfMeals,
            )
        }
    }

    private fun filterPackages() {
        setState { copy(isLoading = true) }
        val filteredPackages = currentState.packages.filter {
            it.durationDays == currentState.selectedPackageDuration && it.mealsPerDay == currentState.selectedNumberOfMeals
        }
        setState { copy(isLoading = false, filteredPackages = filteredPackages) }
    }

    private fun onSubscriptionTypeChanged(type: PackageType) {
        setState {
            copy(
                packageType = type,
                branchTitle = if (type == PackageType.PICKUP) R.string.pickup_branch else R.string.receipt_address,
                categoriesTitle = if (type == PackageType.PICKUP) R.string.plan_categories else R.string.service_categories,
            )
        }
    }

    private fun onCategorySelected(event: SelectPlanEvent.OnCategorySelected) {
        setState { copy(isLoading = true) }
        launchSafe {
            try {
                loadPackages()
                setState {
                    copy(
                        isLoading = false,
                        selectedCategory = event.category,
                    )
                }
            } catch (e: NetworkException) {
                setState { copy(isLoading = false) }
                val errorMessage = if (e.message.isNullOrEmpty()) ErrorDialogState.default.message
                else DeferredString.StringValue(e.message!!)
                throw UiException(
                    dialogState = ErrorDialogState.default.copy(
                        message = errorMessage,
                        onPrimaryBtnClicked = { sendEvent(event) },
                        secondaryButtonText = DeferredString.StringResource(R.string.cancel)
                    )
                )
            }
        }
    }

    private fun onDurationSelected(event: SelectPlanEvent.OnDurationSelected) {
        setState { copy(selectedPackageDuration = event.duration) }
        filterPackages()
    }

    private fun onMealsCountSelected(event: SelectPlanEvent.OnMealsCountSelected) {
        setState { copy(selectedNumberOfMeals = event.numberOfMeals) }
        filterPackages()
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)

        setState { copy(isLoading = false) }
        when (throwable) {
            is UiException -> {
                sendEffect(SelectPlanEffect.ShowError(throwable.dialogState))
            }
            else -> {
                val errorMessage = if (throwable.message.isNullOrEmpty()) ErrorDialogState.default.message
                else DeferredString.StringValue(throwable.message!!)
                sendEffect(
                    SelectPlanEffect.ShowError(
                        dialogState = ErrorDialogState.default.copy(
                            message = errorMessage,
                            primaryButtonText = DeferredString.StringResource(R.string.ok),
                        )
                    )
                )
            }
        }
    }
}
