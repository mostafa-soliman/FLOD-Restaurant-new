package com.fadali.flod_restaurant.feature.subscription.ui.viewpackage

import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetPackageByIdUseCase
import com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.model.MealItem
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class ViewPackageViewModel(
    private val getPackageByIdUseCase: GetPackageByIdUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<ViewPackageState, ViewPackageEvent, ViewPackageEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {

    override fun initialState(): ViewPackageState = ViewPackageState()

    override fun handleEvent(event: ViewPackageEvent) {
        when (event) {
            is ViewPackageEvent.LoadData -> onLoadData(event)
            ViewPackageEvent.OnSelectPlanClicked -> onSelectPlanClicked()
            ViewPackageEvent.OnBackPressed -> sendEffect(ViewPackageEffect.NavigateBack)
            ViewPackageEvent.OnViewPlanMenuClicked -> onViewPlanMenuClicked()
        }
    }

    private fun onLoadData(event: ViewPackageEvent.LoadData) {
        setState { copy(args = event.args) }
        fetchPackageMeals(event.args.packageDetails.id)
    }

    private fun fetchPackageMeals(id: String) {
        if (currentState.packageMeals != null) return

        setState { copy(isLoading = true) }
        launchSafe {
            val response = getPackageByIdUseCase(id)
            val packageMeals = response.meals
            val mealItems = packageMeals?.map { packageMeal ->
                MealItem(
                    imageUrl = packageMeal.imageUrl,
                    name = packageMeal.title,
                    quantity = "x${packageMeal.orderPerDay}"
                )
            }
            setState {
                copy(
                    packageMeals = packageMeals,
                    mealItems = mealItems ?: emptyList(),
                    isLoading = false,
                )
            }
        }
    }

    private fun onSelectPlanClicked() {
        val args = currentState.args ?: return
        val route = SubscriptionRoutes.PlanStartingDate(
            productCategory = args.productCategory,
            packageDetails = args.packageDetails,
            packageType = args.packageType,
            deliveryAddress = args.deliveryAddress,
            pickupBranch = args.pickupBranch
        )
        sendEffect(ViewPackageEffect.NavigateToNextScreen(route))
    }

    private fun onViewPlanMenuClicked() {
        val packageMeals = currentState.packageMeals ?: return
        val route = SubscriptionRoutes.PlanMenu(packageMeals = packageMeals)
        sendEffect(ViewPackageEffect.NavigateToNextScreen(route))
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)

        setState { copy(isLoading = false) }
        val errorMessage = if (throwable.message.isNullOrEmpty()) ErrorDialogState.default.message
        else DeferredString.StringValue(throwable.message!!)
        sendEffect(
            ViewPackageEffect.ShowError(
                dialogState = ErrorDialogState.default.copy(
                    message = errorMessage,
                    onPrimaryBtnClicked = {
                        currentState.args?.let {
                            sendEvent(ViewPackageEvent.LoadData(it))
                        }
                    }
                )
            )
        )
    }
}
