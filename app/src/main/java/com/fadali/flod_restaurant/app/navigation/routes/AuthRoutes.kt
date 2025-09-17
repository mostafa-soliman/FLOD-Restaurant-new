package com.fadali.flod_restaurant.app.navigation.routes

import kotlinx.serialization.Serializable

sealed class AuthRoutes : Route(showBottomNavBar = false) {
    @Serializable
    data object Splash : AuthRoutes()
    @Serializable
    data object Login : AuthRoutes()
    @Serializable
    data object SignUp : AuthRoutes()
    @Serializable
    data class OtpVerification(val phone: String) : AuthRoutes()
    @Serializable
    data object FitStart : AuthRoutes()
    @Serializable
    data object Allergies : AuthRoutes()
    @Serializable
    data object Onboarding : AuthRoutes()
}
