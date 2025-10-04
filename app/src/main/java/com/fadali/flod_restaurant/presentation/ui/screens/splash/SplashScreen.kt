package com.fadali.flod_restaurant.presentation.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val cs = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(AuthRoutes.Login) {
            popUpTo(AuthRoutes.Splash) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(cs.background)
    ) {
        SplashBackground()
        SplashLogo(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun SplashBackground() {
    Image(
        painter = painterResource(id = R.drawable.splash_bg),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun SplashLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "App Logo",
        modifier = modifier
            .size(150.dp)
    )
}
