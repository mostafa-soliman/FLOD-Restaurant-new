package com.fadali.flod_restaurant.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.fadali.flod_restaurant.app.navigation.addScreen
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionDeliveryRoutes

fun NavGraphBuilder.subscriptionDeliveryGraph(navController: NavHostController) {
    addScreen<SubscriptionDeliveryRoutes.CategoriesBrowser> {

    }
}
