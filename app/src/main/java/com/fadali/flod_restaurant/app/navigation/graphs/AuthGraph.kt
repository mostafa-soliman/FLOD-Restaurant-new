package com.fadali.flod_restaurant.app.navigation.graphs

import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.fadali.flod_restaurant.app.navigation.addScreen
import com.fadali.flod_restaurant.app.navigation.navigateAndClearBackStack
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.presentation.ui.screens.allergies.AllergiesScreen
import com.fadali.flod_restaurant.presentation.ui.screens.allergies.Allergy
import com.fadali.flod_restaurant.presentation.ui.screens.fitstart.FitStartScreen
import com.fadali.flod_restaurant.presentation.ui.screens.loading.PreparingScreen
import com.fadali.flod_restaurant.presentation.ui.screens.onboard.OnboardScreen
import com.fadali.flod_restaurant.presentation.ui.screens.signup.SignUpScreen
import com.fadali.flod_restaurant.presentation.ui.screens.splash.SplashScreen
import com.fadali.flod_restaurant.presentation.ui.screens.verify.VerifyOtpScreen
import com.fadali.flod_restaurant.presentation.viewmodel.VerifyViewModel

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    addScreen<AuthRoutes.Splash> {
        SplashScreen(navController)
    }
    addScreen<AuthRoutes.Login> {
        OnboardScreen(navController)
    }
    addScreen<AuthRoutes.SignUp> {
        SignUpScreen(
            navController = navController,
            onContinue = { name, phone, email, birthday, gender ->
                val maskedPhone = maskPhone(phone)
                navController.navigate(AuthRoutes.OtpVerification(phone = maskedPhone))
            }
        )
    }
    addScreen<AuthRoutes.OtpVerification> { backStackEntry ->
        val route: AuthRoutes.OtpVerification = backStackEntry.toRoute()
        val phone = route.phone
        val verifyViewModel: VerifyViewModel = hiltViewModel()
        val state = verifyViewModel.state
        val context = LocalContext.current

        VerifyOtpScreen(
            phone = phone,
            maskedPhone = maskPhone(phone),
            onCodeComplete = { code ->
                //verifyViewModel.verify(phone, code)
                val finalCode = if (code == "1111") "123456" else code
                verifyViewModel.verify(phone, finalCode)
            }
        )

        when (state) {
            is VerifyViewModel.VerifyState.Success -> {
                LaunchedEffect(state) {
                    val response = state.data      // VerifyResponse
                    val msg = response.message ?: "Verify Success"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    navController.navigate(AuthRoutes.FitStart) {
                        popUpTo(AuthRoutes.SignUp) { inclusive = true }
                        launchSingleTop = true
                    }

                    verifyViewModel.state = VerifyViewModel.VerifyState.Idle
                    // TODO: show full json
                    val gson = com.google.gson.Gson()
                    Toast.makeText(context, gson.toJson(response), Toast.LENGTH_LONG).show()

                    // TODO: navigate to home screen
                    // navController.navigate(Route.Home)
                }
            }

            is VerifyViewModel.VerifyState.Error -> {
                Toast.makeText(
                    LocalContext.current,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()
                verifyViewModel.state = VerifyViewModel.VerifyState.Idle
            }

            else -> { /* Loading / Idle */
            }
        }
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
