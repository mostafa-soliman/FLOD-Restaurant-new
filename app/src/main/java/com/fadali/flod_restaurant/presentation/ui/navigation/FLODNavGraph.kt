package com.fadali.flod_restaurant.presentation.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fadali.flod_restaurant.presentation.ui.screens.onboard.OnboardScreen
import com.fadali.flod_restaurant.presentation.ui.screens.splash.SplashScreen
import com.fadali.flod_restaurant.presentation.ui.screens.verify.VerifyOtpScreen

@Composable
fun FLODNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("login") { OnboardScreen(navController) }
        composable("verify_otp") {
            VerifyOtpScreen(
                maskedPhone = "+966xxxxxxxx87",
                onCodeComplete = { code ->
                    // TODO: بعد نجاح التحقق -> انتقل لوجهة أخرى إن حبيت
                    // navController.navigate("home") { popUpTo("login") { inclusive = true } }
                },
                onResend = {
                    // TODO: نادِ API لإعادة إرسال الكود
                }
            )
        }
    }
}
