package com.fadali.flod_restaurant.app.navigation.routes

import com.fadali.flod_restaurant.feature.auth.ui.otp.OtpFlowType
import kotlinx.serialization.Serializable

sealed class AuthRoutes : Route(showBottomNavBar = false) {
    @Serializable
    data object Splash : AuthRoutes()
    @Serializable
    data object Login : AuthRoutes()
    @Serializable
    data class SignUp(val phone: String) : AuthRoutes()
    @Serializable
    data class OtpVerification(
        val phone: String,
        val userId: String = "",
        val flowType: OtpFlowType
    ) : AuthRoutes()
    @Serializable
    data object CompleteUserData : AuthRoutes()
    @Serializable
    data object Allergies : AuthRoutes()
    @Serializable
    data object Onboarding : AuthRoutes()
}
