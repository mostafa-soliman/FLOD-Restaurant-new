package com.fadali.flod_restaurant.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.fadali.flod_restaurant.app.navigation.addScreen
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionPickupRoutes
import com.fadali.flod_restaurant.feature.subscription.selectplan.ui.SelectPlanScreen

fun NavGraphBuilder.subscriptionPickupGraph(navController: NavHostController) {
    addScreen<SubscriptionPickupRoutes.SelectPlan> {
        SelectPlanScreen(navController)
    }
}
