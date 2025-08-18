package com.fadali.flod_restaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.fadali.flod_restaurant.presentation.ui.navigation.FLODNavGraph
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        enableEdgeToEdge()
        setContent {
            FLODRestaurantTheme {
                val systemUi = rememberSystemUiController()
                SideEffect {
                    systemUi.setStatusBarColor(color = Color.White, darkIcons = true)
                    systemUi.setNavigationBarColor(color = Color.White, darkIcons = true)
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    FLODNavGraph(navController)
                }
            }
        }
    }
}