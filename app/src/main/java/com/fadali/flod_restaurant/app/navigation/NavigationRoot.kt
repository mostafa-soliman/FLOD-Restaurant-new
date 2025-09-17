package com.fadali.flod_restaurant.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fadali.flod_restaurant.app.navigation.graphs.Graph
import com.fadali.flod_restaurant.app.navigation.graphs.authGraph
import com.fadali.flod_restaurant.app.navigation.graphs.homeGraph
import com.fadali.flod_restaurant.app.navigation.graphs.moreGraph
import com.fadali.flod_restaurant.app.navigation.graphs.plansGraph
import com.fadali.flod_restaurant.app.navigation.graphs.profileGraph

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Graph.AuthGraph,
        modifier = modifier
    ) {
        addGraph<Graph.AuthGraph> {
            authGraph(navController = navController)
        }
        addGraph<Graph.HomeGraph> {
            homeGraph(navController = navController)
        }
        addGraph<Graph.PlansGraph> {
            plansGraph(navController = navController)
        }
        addGraph<Graph.ProfileGraph> {
            profileGraph(navController = navController)
        }
        addGraph<Graph.MoreGraph> {
            moreGraph(navController = navController)
        }
    }
}
