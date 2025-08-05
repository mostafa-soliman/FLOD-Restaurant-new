package com.fadali.flod_restaurant.presentation.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fadali.flod_restaurant.presentation.ui.screens.splash.SplashScreen

@Composable
fun FLODNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen(navController) }
        //composable("login") { LoginScreen(navController) }
    }
}
