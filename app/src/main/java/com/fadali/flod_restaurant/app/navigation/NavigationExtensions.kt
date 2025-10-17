package com.fadali.flod_restaurant.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.fadali.flod_restaurant.app.MainViewModel
import com.fadali.flod_restaurant.app.navigation.graphs.Graph
import com.fadali.flod_restaurant.app.navigation.routes.Route
import org.koin.compose.koinInject
import kotlin.reflect.KType

private const val BACK_STACK_ROOT = 0

/**
 * Create new Graph for new feature modules
 */
inline fun <reified T: Graph> NavGraphBuilder.addGraph(
    crossinline graphContent: NavGraphBuilder.() -> Unit
){
    val graphInstance = Graph.getInstance<T>()
    navigation<T>(
        startDestination = graphInstance.startDestination
    ){
        graphContent()
    }
}

/**
 * Enhanced composable that automatically updates MainViewModel with current route
 */
inline fun <reified T : Route> NavGraphBuilder.addScreen(
    typeMap: Map<KType, NavType<*>> = emptyMap(),
    crossinline content: @Composable (NavBackStackEntry) -> Unit
) {
    composable<T>(typeMap = typeMap) { backStackEntry ->
        val mainViewModel: MainViewModel = koinInject()
        val currentRoute = backStackEntry.toRoute<T>()
        LaunchedEffect(currentRoute) {
            mainViewModel.updateCurrentRoute(currentRoute)
        }

        content(backStackEntry)
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
 * Navigate and clear the entire back stack
 */
fun NavController.navigateAndClearBackStack(graph: Graph) {
    navigate(graph) {
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
