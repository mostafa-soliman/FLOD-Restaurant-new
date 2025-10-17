package com.fadali.flod_restaurant.app.navigation.routes

import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import kotlinx.serialization.Serializable

@Serializable
sealed class SubscriptionRoutes(private val showBottomBar: Boolean) : Route(showBottomNavBar = showBottomBar) {
    @Serializable
    data object SelectPlan : SubscriptionRoutes(showBottomBar = true)

    @Serializable
    data class BranchAddress(
        val packageType: PackageType,
        val customerAddresses: List<CustomerAddress>,
        val branches: List<Branch>
    ) : SubscriptionRoutes(showBottomBar = false)

    @Serializable
    data object CreateNewAddress : SubscriptionRoutes(showBottomBar = false)

    @Serializable
    data object PackageDetails : SubscriptionRoutes(showBottomBar = true)

    @Serializable
    data object PlanMenu : SubscriptionRoutes(showBottomBar = true)

    @Serializable
    data object PickupDates : SubscriptionRoutes(showBottomBar = true)

    @Serializable
    data object Review : SubscriptionRoutes(showBottomBar = false)

    @Serializable
    data object TermsAndConditions : SubscriptionRoutes(showBottomBar = false)

    @Serializable
    data object Payment : SubscriptionRoutes(showBottomBar = false)

    @Serializable
    data object PackagePlan : SubscriptionRoutes(showBottomBar = false)
}
