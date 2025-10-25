package com.fadali.flod_restaurant.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.fadali.flod_restaurant.app.navigation.addScreen
import com.fadali.flod_restaurant.app.navigation.routes.BottomNavRoutes
import com.fadali.flod_restaurant.app.navigation.routes.PlanRoutes
import com.fadali.flod_restaurant.feature.plan.ui.packagedetails.PackageDetailsScreen

fun NavGraphBuilder.plansGraph(navController: NavHostController) {
    addScreen<BottomNavRoutes.Plans> {
    }

    addScreen<PlanRoutes.PackageDetails> { navBackStackEntry ->
        val args = navBackStackEntry.toRoute<PlanRoutes.PackageDetails>()
        PackageDetailsScreen(
            navController = navController,
            args = args
        )
    }
}
