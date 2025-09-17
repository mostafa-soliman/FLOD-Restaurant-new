package com.fadali.flod_restaurant.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.fadali.flod_restaurant.app.navigation.addScreen
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionPickupRoutes

fun NavGraphBuilder.subscriptionPickupGraph(navController: NavHostController) {
    addScreen<SubscriptionPickupRoutes.CategoriesBrowser> {

    }
}
