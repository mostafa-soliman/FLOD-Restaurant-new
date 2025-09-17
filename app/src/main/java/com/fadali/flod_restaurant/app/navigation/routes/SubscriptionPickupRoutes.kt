package com.fadali.flod_restaurant.app.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class SubscriptionPickupRoutes(private val showBottomBar: Boolean) : Route(showBottomNavBar = showBottomBar) {
    @Serializable
    data object CategoriesBrowser : SubscriptionPickupRoutes(showBottomBar = true)
    @Serializable
    data object SelectPickupBranch : SubscriptionPickupRoutes(showBottomBar = false)
    @Serializable
    data object PackageDetails : SubscriptionPickupRoutes(showBottomBar = true)
    @Serializable
    data object PlanMenu : SubscriptionPickupRoutes(showBottomBar = true)
    @Serializable
    data object PickupDates : SubscriptionPickupRoutes(showBottomBar = true)
    @Serializable
    data object Review : SubscriptionPickupRoutes(showBottomBar = false)
    @Serializable
    data object TermsAndConditions : SubscriptionPickupRoutes(showBottomBar = false)
    @Serializable
    data object Payment : SubscriptionPickupRoutes(showBottomBar = false)
    @Serializable
    data object PackagePlan : SubscriptionPickupRoutes(showBottomBar = false)
}
