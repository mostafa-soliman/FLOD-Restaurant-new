package com.fadali.flod_restaurant.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.fadali.flod_restaurant.app.navigation.graphs.Graph
import com.fadali.flod_restaurant.app.navigation.routes.Route

private const val BACK_STACK_ROOT = 0

/**
 * Create new Graph for new feature modules
 */
inline fun <reified T: Graph> NavGraphBuilder.addGraph(
    startDestination: Route,
    crossinline graphContent: NavGraphBuilder.() -> Unit
){
    navigation<T>(
        startDestination = startDestination
    ){
        graphContent()
    }
}

/**
 * Navigate and clear the entire back stack
 */
fun NavController.navigateAndClearBackStack(route: Route) {
    navigate(route) {
        popUpTo(BACK_STACK_ROOT) {
            inclusive = true
        }
    }
}

/**
 * Navigate and replace current screen
 */
fun NavController.navigateAndReplace(route: Route) {
    navigate(route) {
        popUpTo(currentDestination?.route ?: return@navigate) {
            inclusive = true
        }
    }
}

/**
 * Safe navigation that prevents multiple clicks
 */
fun NavController.safeNavigate(
    route: Route,
    navOptions: NavOptions? = null
) {
    if (currentDestination?.route != route.toString()) {
        navigate(route, navOptions)
    }
}

/**
 * Safe back navigation
 */
fun NavController.navigateBack(){
    navigateUp()
}
