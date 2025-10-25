package com.fadali.flod_restaurant.app.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class PlanRoutes(
    private val showBottomBar: Boolean
) : Route(showBottomNavBar = showBottomBar) {

    @Serializable
    data class PackageDetails(
        val subscriptionId: String? = null
    ) : PlanRoutes(showBottomBar = false)
}
