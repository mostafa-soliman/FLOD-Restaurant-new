package com.fadali.flod_restaurant.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.fadali.flod_restaurant.app.navigation.addScreen
import com.fadali.flod_restaurant.app.navigation.routes.BottomNavRoutes
import com.fadali.flod_restaurant.feature.plan.ui.myplans.MyPlansScreen

fun NavGraphBuilder.plansGraph(navController: NavHostController) {
    addScreen<BottomNavRoutes.Plans> {
        MyPlansScreen(navController = navController)
    }
}
