package com.fadali.flod_restaurant.app.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class SubscriptionDeliveryRoutes(private val showBottomBar: Boolean) : Route(showBottomNavBar = showBottomBar) {
    @Serializable
    data object CategoriesBrowser : SubscriptionDeliveryRoutes(showBottomBar = true)
    @Serializable
    data object SelectDeliveryAddress : SubscriptionDeliveryRoutes(showBottomBar = false)
    @Serializable
    data object CreateNewAddress : SubscriptionDeliveryRoutes(showBottomBar = false)
    @Serializable
    data object PackageDetails : SubscriptionDeliveryRoutes(showBottomBar = true)
    @Serializable
    data object PlanMenu : SubscriptionDeliveryRoutes(showBottomBar = true)
    @Serializable
    data object PickupDates : SubscriptionDeliveryRoutes(showBottomBar = true)
    @Serializable
    data object Review : SubscriptionDeliveryRoutes(showBottomBar = false)
    @Serializable
    data object TermsAndConditions : SubscriptionDeliveryRoutes(showBottomBar = false)
    @Serializable
    data object Payment : SubscriptionDeliveryRoutes(showBottomBar = false)
    @Serializable
    data object PackagePlan : SubscriptionDeliveryRoutes(showBottomBar = false)
}
