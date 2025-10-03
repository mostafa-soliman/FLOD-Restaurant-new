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
import com.fadali.flod_restaurant.presentation.ui.screens.allergies.AllergiesScreen
import com.fadali.flod_restaurant.presentation.ui.screens.allergies.Allergy
import com.fadali.flod_restaurant.presentation.ui.screens.fitstart.FitStartScreen
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
    addScreen<AuthRoutes.FitStart> {
        FitStartScreen(
            onLogin = { _, _, _ ->
                navController.navigate(AuthRoutes.Allergies) {
                    popUpTo(AuthRoutes.SignUp) { inclusive = true }
                    launchSingleTop = true
                }
            },
            onSkip = {
                navController.navigate(AuthRoutes.Allergies) {
                    popUpTo(AuthRoutes.SignUp) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }
    addScreen<AuthRoutes.Allergies> {

        // مؤقتًا: بيانات محلية — بدّلها بتحميل من ViewModel لاحقًا
        val demoItems = listOf(
            Allergy("eggs", "Eggs", "🥚"),
            Allergy("milk", "Lactose", "🥛"),
            Allergy("chicken", "Chicken", "🍗"),
            Allergy("spice", "Spice", "🌶️"),
            Allergy("meat", "Meat", "🥩"),
            Allergy("gluten", "Gluten", "🍞"),
            Allergy("fish", "Fish", "🐟"),
            Allergy("peanuts", "Peanuts", "🥜"),
            Allergy("tree_nuts", "Tree Nuts", "🌰"),
            Allergy("shellfish", "Shellfish", "🦐"),
            Allergy("soy", "Soy", "🌱"),
            Allergy("mold", "Mold", "🍄"),
            Allergy("peach", "Peach", "🍑"),
            Allergy("corn", "Corn", "🌽"),
            Allergy("apple", "Apple", "🍎"),
            Allergy("broccoli", "Broccoli", "🥦"),
            Allergy("strawberries", "Strawberries", "🍓"),
        )

        AllergiesScreen(
            items = demoItems,
            onStart = { selected ->
                // TODO: احفظ الاختيارات ووجّه للهوم الحقيقي
                // navController.navigate(Route.Home) { popUpTo(Route.Splash){ inclusive = true } }
                navController.navigate(AuthRoutes.Onboarding) {
                    launchSingleTop = true
                }
            }
        )
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
