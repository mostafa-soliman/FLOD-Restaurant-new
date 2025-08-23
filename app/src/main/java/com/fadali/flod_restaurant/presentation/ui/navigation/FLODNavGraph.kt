package com.fadali.flod_restaurant.presentation.ui.navigation

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fadali.flod_restaurant.presentation.ui.screens.fitstart.FitStartScreen
import com.fadali.flod_restaurant.presentation.ui.screens.onboard.OnboardScreen
import com.fadali.flod_restaurant.presentation.ui.screens.signup.SignUpScreen
import com.fadali.flod_restaurant.presentation.ui.screens.splash.SplashScreen
import com.fadali.flod_restaurant.presentation.ui.screens.verify.VerifyOtpScreen
import com.fadali.flod_restaurant.presentation.viewmodel.VerifyViewModel

object Route {
    const val Splash = "splash"
    const val Login = "login"
    const val SignUp = "signup"
    const val VerifyOtp = "verify_otp"
    const val FitStart = "fit_start"
}

@Composable
fun FLODNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash
    ) {
        composable(Route.Splash) {
            SplashScreen(navController)
        }

        composable(Route.Login) {
            OnboardScreen(navController)
        }

        composable(Route.SignUp) {
            SignUpScreen(
                navController = navController,
                onContinue = { name, fullPhone, email, bday, gender ->
                    val masked = maskPhone(fullPhone)
                    navController.navigate("${Route.VerifyOtp}?phone=${Uri.encode(masked)}")

                }
            )
        }

        composable(
            route = "${Route.VerifyOtp}/{phone}",
            arguments = listOf(navArgument("phone"){ type = NavType.StringType })
        ){ backStackEntry ->

            val phone = backStackEntry.arguments?.getString("phone") ?: ""
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

            when(state){
                is VerifyViewModel.VerifyState.Success -> {
                    LaunchedEffect(state) {
                        val response = state.data      // VerifyResponse
                        val msg = response.message ?: "Verify Success"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        navController.navigate(Route.FitStart) {
                            popUpTo(Route.SignUp) { inclusive = true }
                            launchSingleTop = true
                        }

                        verifyViewModel.state = VerifyViewModel.VerifyState.Idle
                        // ✅ show full json
                        val gson = com.google.gson.Gson()
                        Toast.makeText(context, gson.toJson(response), Toast.LENGTH_LONG).show()

                        // TODO: navigate to home screen
                        // navController.navigate(Route.Home)
                    }
//                    navController.navigate(Route.Login){
//                        popUpTo(Route.Login){ inclusive = true }
//                    }
                }
                is VerifyViewModel.VerifyState.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    verifyViewModel.state = VerifyViewModel.VerifyState.Idle
                }
                else -> { /* Loading / Idle */ }
            }
        }
        composable(Route.FitStart) {
            FitStartScreen(
                onLogin = { _,_,_ -> /* TODO: أكمل الخطوة التالية (حفظ وإلى الهوم) */ },
                onSkip  = { /* optional */ }
            )
        }

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
