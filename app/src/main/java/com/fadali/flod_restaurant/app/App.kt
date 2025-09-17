package com.fadali.flod_restaurant.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.fadali.flod_restaurant.app.navigation.NavigationRoot
import com.fadali.flod_restaurant.core.component.bottomnavbar.BottomNavBar
import com.fadali.flod_restaurant.core.component.bottomnavbar.BottomNavConfig
import com.fadali.flod_restaurant.i18n.LocalizationProvider
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.compose.koinInject

@Composable
fun App() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = koinInject()
    val showBottomBar by mainViewModel.showBottomBar.collectAsState()

    FLODRestaurantTheme {
        LocalizationProvider {
            val systemUi = rememberSystemUiController()
            SideEffect {
                systemUi.setStatusBarColor(color = Color.Transparent, darkIcons = true)
                systemUi.setNavigationBarColor(color = Color.Transparent, darkIcons = true)
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    if (showBottomBar) {
                        BottomNavBar(
                            navController = navController,
                            items = BottomNavConfig.items,
                        )
                    }
                }
            ) { paddingValues ->
                NavigationRoot(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController
                )
            }
        }
    }
}
