package com.fadali.flod_restaurant.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fadali.flod_restaurant.app.navigation.graphs.Graph
import com.fadali.flod_restaurant.app.navigation.graphs.tempGraph

@Composable
fun NavigationRoot(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Graph.Temp
    ) {
        addGraph<Graph.Temp>(
            startDestination = Graph.Temp.startDestination,
            graphContent = { tempGraph(navController = navController) }
        )
    }
}
