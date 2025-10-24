package com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.core.ui.exception.UiException
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.GetPackagesRequest
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetBranchesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetCustomerAddressesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetPackagesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetProductCategoriesUseCase
import com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui.utils.BranchAddressResult
import com.smyvo.core.network.NetworkException
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

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
            is SelectPlanEvent.ProcessBranchResult -> processBranchResult(event.result)
            is SelectPlanEvent.OnSubscriptionTypeChanged -> onSubscriptionTypeChanged(event)
            is SelectPlanEvent.OnCategorySelected -> onCategorySelected(event)
            is SelectPlanEvent.OnDurationSelected -> onDurationSelected(event)
            is SelectPlanEvent.OnMealsCountSelected -> onMealsCountSelected(event)
            is SelectPlanEvent.OnPackageSelected -> onPackageSelected(event)
            is SelectPlanEvent.OnPackageCardClicked -> onPackageCardClicked(event)
            SelectPlanEvent.OnEditAddressClicked -> onEditAddressClicked()
        }
    }

    private fun loadData() {
        launchSafe {
            setState { copy(isLoading = true) }
            try {
                loadCustomerAddresses()
                loadBranches()
                loadProductCategories()
                loadPackages()
            } catch (e: NetworkException.ClientException) {
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
        val addresses = getCustomerAddressesUseCase()
        val selectedAddress = if (currentState.selectedDeliveryAddress != null) {
            currentState.selectedDeliveryAddress
        } else {
            if (addresses.isNotEmpty()) addresses.first() else currentState.selectedDeliveryAddress
        }
        setState {
            copy(
                customerAddresses = addresses,
                selectedDeliveryAddress = selectedAddress
            )
        }
    }

    private suspend fun loadBranches() {
        val branches = getBranchesUseCase()
        setState {
            copy(
                branches = branches,
                selectedBranch = if (branches.isNotEmpty()) branches.first() else selectedBranch
            )
        }
    }

    private suspend fun loadProductCategories() {
        val categories = getProductCategoriesUseCase()
        setState {
            copy(
                categories = categories,
                selectedCategory = if (categories.isNotEmpty()) categories.first() else selectedCategory
            )
        }
    }

    private suspend fun loadPackages() {
        val request = GetPackagesRequest(
            packageType = currentState.packageType.value,
            productCategoryId = currentState.selectedCategory?.id ?: throw NetworkException.ClientException(),
            customerAddressId = currentState.selectedDeliveryAddress?.id,
            branchId = currentState.selectedBranch?.id ?: throw NetworkException.ClientException(),
        )
        val packages = getPackagesUseCase(request)
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

    private fun onSubscriptionTypeChanged(event: SelectPlanEvent.OnSubscriptionTypeChanged) {
        setState {
            copy(
                packageType = event.type,
                branchTitle = if (event.type == PackageType.PICKUP) R.string.pickup_branch else R.string.receipt_address,
                categoriesTitle = if (event.type == PackageType.PICKUP) R.string.plan_categories else R.string.service_categories,
            )
        }
        launchSafe {
            setState { copy(isLoading = true) }
            try {
                loadPackages()
                setState { copy(isLoading = false) }
            } catch (e: NetworkException.ClientException) {
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

    private fun onCategorySelected(event: SelectPlanEvent.OnCategorySelected) {
        if (event.category.id == currentState.selectedCategory?.id) return

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
            } catch (e: NetworkException.ClientException) {
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

    private fun onEditAddressClicked() {
        sendEffect(
            SelectPlanEffect.NavigateToNextScreen(
                route = SubscriptionRoutes.BranchAddress(
                    packageType = currentState.packageType,
                    customerAddresses = currentState.customerAddresses,
                    branches = currentState.branches
                )
            )
        )
    }

    private fun processBranchResult(result: BranchAddressResult) {
        setState {
            copy(
                selectedDeliveryAddress = result.deliveryAddress ?: selectedDeliveryAddress,
                selectedBranch = result.selectedBranch ?: selectedBranch,
            )
        }

        if (currentState.selectedCategory != null &&
            (currentState.selectedDeliveryAddress != null || currentState.selectedBranch != null)
        ) {
            launchSafe {
                try {
                    loadPackages()
                } catch (e: NetworkException.ClientException) {
                    val errorMessage = if (e.message.isNullOrEmpty()) ErrorDialogState.default.message
                    else DeferredString.StringValue(e.message!!)
                    throw UiException(
                        dialogState = ErrorDialogState.default.copy(
                            message = errorMessage,
                            onPrimaryBtnClicked = { processBranchResult(result) },
                            secondaryButtonText = DeferredString.StringResource(R.string.cancel)
                        )
                    )
                }
            }
        }
    }

    private fun onPackageSelected(event: SelectPlanEvent.OnPackageSelected) {
        val route = SubscriptionRoutes.PlanStartingDate(
            productCategory = currentState.selectedCategory!!,
            packageDetails = event.packageDetails,
            packageType = currentState.packageType,
            deliveryAddress = if (currentState.packageType == PackageType.DELIVERY) {
                currentState.selectedDeliveryAddress
            } else null,
            pickupBranch = if (currentState.packageType == PackageType.PICKUP) {
                currentState.selectedBranch
            } else null,
        )

        sendEffect(SelectPlanEffect.NavigateToNextScreen(route = route))
    }

    private fun onPackageCardClicked(event: SelectPlanEvent.OnPackageCardClicked) {
        val route = SubscriptionRoutes.ViewPackage(
            packageDetails = event.packageDetails,
            productCategory = currentState.selectedCategory!!,
            packageType = currentState.packageType,
            deliveryAddress = currentState.selectedDeliveryAddress,
            pickupBranch = currentState.selectedBranch
        )

        sendEffect(SelectPlanEffect.NavigateToNextScreen(route = route))
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
