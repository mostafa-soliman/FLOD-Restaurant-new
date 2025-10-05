package com.fadali.flod_restaurant.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.fadali.flod_restaurant.app.navigation.addScreen
import com.fadali.flod_restaurant.app.navigation.navigateAndClearBackStack
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.feature.auth.ui.login.LoginScreen
import com.fadali.flod_restaurant.feature.auth.ui.otp.OTPVerificationScreen
import com.fadali.flod_restaurant.feature.auth.ui.signup.SignUpScreen
import com.fadali.flod_restaurant.feature.profile.ui.allergies.AllergiesScreen
import com.fadali.flod_restaurant.feature.profile.ui.completeuserdata.CompleteUserDataScreen
import com.fadali.flod_restaurant.presentation.ui.screens.loading.PreparingScreen
import com.fadali.flod_restaurant.presentation.ui.screens.splash.SplashScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    addScreen<AuthRoutes.Splash> {
        SplashScreen(navController)
    }
    addScreen<AuthRoutes.Login> {
        LoginScreen(navController)
    }
    addScreen<AuthRoutes.SignUp> {
        SignUpScreen(navController)
    }
    addScreen<AuthRoutes.OtpVerification> { backStackEntry ->
        val args: AuthRoutes.OtpVerification = backStackEntry.toRoute()
        OTPVerificationScreen(
            navController = navController,
            args = args
        )
    }
    addScreen<AuthRoutes.CompleteUserData> {
        CompleteUserDataScreen(navController)
    }
    addScreen<AuthRoutes.Allergies> {
        AllergiesScreen(navController)
    }
    addScreen<AuthRoutes.Onboarding> {
        PreparingScreen(
            onFinish = {
                navController.navigateAndClearBackStack(Graph.HomeGraph)
            }
        )
    }
}

private fun maskPhone(phone: String): String {
    val clean = phone.trim()
    return if (clean.length > 6) {
        val head = clean.take(4)
        val tail = clean.takeLast(2)
        "$head${"x".repeat(clean.length - 6)}$tail"
    } else clean
}
