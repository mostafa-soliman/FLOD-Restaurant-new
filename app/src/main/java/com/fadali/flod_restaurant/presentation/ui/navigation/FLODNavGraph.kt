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
            route = "${Route.VerifyOtp}/{phone}",
            arguments = listOf(navArgument("phone"){ type = NavType.StringType })
        ){ backStackEntry ->

            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            val verifyViewModel: VerifyViewModel = hiltViewModel()
            val state = verifyViewModel.state
            val context = LocalContext.current

            VerifyOtpScreen(
                phone = phone,                      // <-- نمرّر رقم الموبايل نفسه
                maskedPhone = maskPhone(phone),
                onCodeComplete = { code ->          // <-- لما المستخدم يدخل الكود كامل
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
                        // توست الرسالة
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

                        // ✅ show full json
                        val gson = com.google.gson.Gson()
                        Toast.makeText(context, gson.toJson(response), Toast.LENGTH_LONG).show()

                        // TODO: navigate to home screen
                        // navController.navigate(Route.Home)
                    }
                    // مثال: انتقل للصفحة الرئيسية أو Profile بعد التأكيد
//                    navController.navigate(Route.Login){
//                        popUpTo(Route.Login){ inclusive = true }
//                    }
                }
                is VerifyViewModel.VerifyState.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        (state as VerifyViewModel.VerifyState.Error).message,
                        Toast.LENGTH_SHORT
                    ).show()
                    verifyViewModel.state = VerifyViewModel.VerifyState.Idle
                }
                else -> { /* Loading / Idle */ }
            }
        }


//        composable(
//            route = "${Route.VerifyOtp}?phone={phone}",
//            arguments = listOf(
//                navArgument("phone") { defaultValue = "+966xxxxxxxx87" }
//            )
//        ) { backStackEntry ->
//            val masked = backStackEntry.arguments?.getString("phone") ?: "+966xxxxxxxx87"
//            VerifyOtpScreen(
//                maskedPhone = masked,
//                onCodeComplete = { code ->
//                    // TODO: بعد تأكيد الكود، وجّه المستخدم (مثلاً للـ Home)
//                    // navController.navigate("home") { popUpTo(Route.Login) { inclusive = true } }
//                },
//                onResend = {
//                    // TODO: نادِ API لإعادة الإرسال
//                }
//            )
//        }
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
