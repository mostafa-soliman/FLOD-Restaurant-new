package com.fadali.flod_restaurant.feature.plan.ui.packagedetails

import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.feature.plan.ui.packagedetails.mapper.toAddOns
import com.fadali.flod_restaurant.feature.plan.ui.packagedetails.mapper.toMenuItems
import com.fadali.flod_restaurant.feature.plan.ui.packagedetails.mapper.toPackageDays
import com.fadali.flod_restaurant.feature.plan.ui.packagedetails.mapper.toPackageInfo
import com.fadali.flod_restaurant.feature.plan.domain.usecase.GetPackageDetailsUseCase
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler


class PackageDetailsViewModel(
    private val getPackageDetailsUseCase: GetPackageDetailsUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<PackageDetailsState, PackageDetailsEvent, PackageDetailsEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {

    override fun initialState(): PackageDetailsState = PackageDetailsState()

    override fun handleEvent(event: PackageDetailsEvent) {
        when (event) {
            is PackageDetailsEvent.Initialize -> handleInitialize(event.subscriptionId)
            PackageDetailsEvent.LoadPackageDetails -> handleLoadPackageDetails()

            is PackageDetailsEvent.Load -> loadPackageDetails(event.subscriptionId)
            is PackageDetailsEvent.OnDaySelected -> handleDaySelection(event.index)
            is PackageDetailsEvent.OnCustomizeMenuClick -> {
                sendEffect(PackageDetailsEffect.NavigateToCustomize(event.menuItemId))
            }

            is PackageDetailsEvent.OnFreezePackageClick -> handleFreezePackage(event.subscriptionId)
            is PackageDetailsEvent.OnAddOnQuantityChange -> handleAddOnQuantityChange(
                event.addOnId,
                event.quantity
            )

            PackageDetailsEvent.OnBackClick -> sendEffect(PackageDetailsEffect.NavigateBack)
        }
    }

    private fun handleInitialize(subscriptionId: String?) {
        if (subscriptionId != null) {
            loadPackageDetails(subscriptionId)
        } else {
            handleLoadPackageDetails()
        }
    }

    private fun loadPackageDetails(subscriptionId: String) {
        setState { copy(isLoading = true, subscriptionId = subscriptionId) }
        launchSafe {
            val response = getPackageDetailsUseCase(subscriptionId)
            val packageInfo = response.toPackageInfo()
            val packageDays = response.toPackageDays()
            val menuItems = response.toMenuItems(selectedDayIndex = 0)
            val addOns = response.toAddOns()
            setState {
                copy(
                    isLoading = false,
                    packageInfo = packageInfo,
                    packageDays = packageDays,
                    selectedDayIndex = 0,
                    todayMenuItems = menuItems,
                    addOns = addOns
                )
            }
        }
    }

    private fun handleLoadPackageDetails() {
        val subscriptionId = currentState.subscriptionId
        if (subscriptionId != null) {
            loadPackageDetails(subscriptionId)
        } else {
            sendEffect(
                PackageDetailsEffect.ShowError(
                    ErrorDialogState.default.copy(
                        message = com.fadali.flod_restaurant.core.util.DeferredString.StringResource(
                            com.fadali.flod_restaurant.R.string.error_subscription_id_missing
                        )
                    )
                )
            )
        }
    }

    private fun handleDaySelection(index: Int) {
        setState { copy(selectedDayIndex = index) }

        launchSafe {
            val subscriptionId = currentState.subscriptionId ?: return@launchSafe
            val response = getPackageDetailsUseCase(subscriptionId)
            val menuItems = response.toMenuItems(selectedDayIndex = index)

            setState {
                copy(todayMenuItems = menuItems)
            }
        }
    }

    private fun handleFreezePackage(subscriptionId: String) {
        launchSafe {
            try {
                setState { copy(isLoading = true) }
                setState { copy(isLoading = false) }
            } catch (t: Throwable) {
                handleException(t)
            }
        }
    }

    private fun handleAddOnQuantityChange(addOnId: String, quantity: Int) {
        setState {
            copy(
                addOns = addOns.map { addOn ->
                    if (addOn.id == addOnId) addOn.copy(quantity = quantity)
                    else addOn
                }
            )
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }
        sendEffect(
            PackageDetailsEffect.ShowError(
                ErrorDialogState.default.copy(
                    message = com.fadali.flod_restaurant.core.util.DeferredString.StringValue(
                        throwable.message ?: "Unknown error occurred"
                    )
                )
            )
        )
    }
}