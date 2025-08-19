package com.fadali.flod_restaurant.presentation.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fadali.flod_restaurant.presentation.ui.screens.onboard.OnboardScreen
import com.fadali.flod_restaurant.presentation.ui.screens.signup.SignUpScreen
import com.fadali.flod_restaurant.presentation.ui.screens.splash.SplashScreen
import com.fadali.flod_restaurant.presentation.ui.screens.verify.VerifyOtpScreen

object Route {
    const val Splash = "splash"
    const val Login = "login"           // Onboard
    const val SignUp = "signup"
    const val VerifyOtp = "verify_otp"  // + optional ?phone=
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
            OnboardScreen(navController) // زر Login يروح verify_otp, وزر SignUp يروح signup
        }

        composable(Route.SignUp) {
            SignUpScreen(
                navController = navController,
                onContinue = { name, fullPhone, email, bday, gender ->
                    // ابعت رقم مقنّع/حقيقي حسب لوجيكك:
                    val masked = maskPhone(fullPhone)
                    navController.navigate("${Route.VerifyOtp}?phone=${Uri.encode(masked)}")
                }
            )
        }

        composable(
            route = "${Route.VerifyOtp}?phone={phone}",
            arguments = listOf(
                navArgument("phone") { defaultValue = "+966xxxxxxxx87" }
            )
        ) { backStackEntry ->
            val masked = backStackEntry.arguments?.getString("phone") ?: "+966xxxxxxxx87"
            VerifyOtpScreen(
                maskedPhone = masked,
                onCodeComplete = { code ->
                    // TODO: بعد تأكيد الكود، وجّه المستخدم (مثلاً للـ Home)
                    // navController.navigate("home") { popUpTo(Route.Login) { inclusive = true } }
                },
                onResend = {
                    // TODO: نادِ API لإعادة الإرسال
                }
            )
        }
    }
}

// اختياري: إخفاء أرقام الهاتف (اعمل لوجيكك كما تحب)
private fun maskPhone(phone: String): String {
    // مثال بسيط: أبقِ أول 4 + آخر 2
    val clean = phone.trim()
    return if (clean.length > 6) {
        val head = clean.take(4)
        val tail = clean.takeLast(2)
        "$head${"x".repeat(clean.length - 6)}$tail"
    } else clean
}
