package com.fadali.flod_restaurant.feature.subscription.ui.planmenu

import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler

class PlanMenuViewModel(
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<PlanMenuState, PlanMenuEvent, PlanMenuEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {

    override fun initialState(): PlanMenuState = PlanMenuState()

    override fun handleEvent(event: PlanMenuEvent) {
        when (event) {
            is PlanMenuEvent.LoadData -> loadData(event)
            PlanMenuEvent.OnBackPressed -> {
                sendEffect(PlanMenuEffect.NavigateBack)
            }
        }
    }

    private fun loadData(event: PlanMenuEvent.LoadData) {
        val mealsByDay = event.args.packageMeals.groupBy { meal ->
            meal.serialDay?.toString() ?: ""
        }.mapValues { (_, meals) ->
            meals.map { packageMeal ->
                MealItemData(
                    id = packageMeal.id ?: "",
                    name = packageMeal.title ?: "",
                    imageUrl = packageMeal.imageUrl ?: "",
                    quantity = "x${packageMeal.orderPerDay ?: -1}",
                    price = packageMeal.price ?: 0.0
                )
            }
        }

        setState { copy(mealsByDay = mealsByDay) }
    }

}
