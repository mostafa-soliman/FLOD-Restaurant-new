package com.fadali.flod_restaurant.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.fadali.flod_restaurant.app.navigation.addGraph
import com.fadali.flod_restaurant.app.navigation.addScreen
import com.fadali.flod_restaurant.app.navigation.routes.BottomNavRoutes
import com.fadali.flod_restaurant.feature.home.ui.HomeScreen

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    addScreen<BottomNavRoutes.Home> {
        HomeScreen(navController)
    }
    addGraph<Graph.SubscriptionGraph> {
        subscriptionGraph(navController = navController)
    }
}
