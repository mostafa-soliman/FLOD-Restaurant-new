package com.fadali.flod_restaurant.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.fadali.flod_restaurant.app.navigation.addGraph
import com.fadali.flod_restaurant.app.navigation.addScreen
import com.fadali.flod_restaurant.app.navigation.routes.BottomNavRoutes
import com.fadali.flod_restaurant.presentation.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    addScreen<BottomNavRoutes.Home> {
        HomeScreen()
    }
    addGraph<Graph.SubscriptionPickupGraph> {
        subscriptionPickupGraph(navController = navController)
    }
    addGraph<Graph.SubscriptionDeliveryGraph> {
        subscriptionDeliveryGraph(navController = navController)
    }
}
