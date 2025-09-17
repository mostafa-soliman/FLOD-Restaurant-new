package com.fadali.flod_restaurant.app.navigation.routes

import kotlinx.serialization.Serializable

sealed class BottomNavRoutes : Route(showBottomNavBar = true) {
    @Serializable
    data object Home : BottomNavRoutes()
    @Serializable
    data object Plans : BottomNavRoutes()
    @Serializable
    data object Profile : BottomNavRoutes()
    @Serializable
    data object More : BottomNavRoutes()
}
