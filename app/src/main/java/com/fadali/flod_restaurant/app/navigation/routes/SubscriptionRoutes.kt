package com.fadali.flod_restaurant.app.navigation.routes

import com.fadali.flod_restaurant.core.network.appconfig.DayOfWeek
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageMeal
import com.fadali.flod_restaurant.feature.subscription.domain.model.timeslots.DeliveryTimeSlot
import kotlinx.serialization.Serializable

@Serializable
sealed class SubscriptionRoutes(
    private val showBottomBar: Boolean
) : Route(showBottomNavBar = showBottomBar) {

    @Serializable
    data object SelectPlan : SubscriptionRoutes(showBottomBar = true)

    @Serializable
    data class BranchAddress(
        val packageType: PackageType,
        val customerAddresses: List<CustomerAddress>,
        val branches: List<Branch>
    ) : SubscriptionRoutes(showBottomBar = false)

    @Serializable
    data class ViewPackage(
        val productCategory: ProductCategory,
        val packageDetails: PackageDetails,
        val packageType: PackageType,
        val deliveryAddress: CustomerAddress?,
        val pickupBranch: Branch?,
    ) : SubscriptionRoutes(showBottomBar = false)

    @Serializable
    data class PlanMenu(
        val packageMeals: List<PackageMeal>
    ) : SubscriptionRoutes(showBottomBar = false)

    @Serializable
    data class PlanStartingDate(
        val productCategory: ProductCategory,
        val packageDetails: PackageDetails,
        val packageType: PackageType,
        val deliveryAddress: CustomerAddress?,
        val pickupBranch: Branch?,
    ) : SubscriptionRoutes(showBottomBar = false)

    @Serializable
    data class SubscriptionReview(
        val productCategory: ProductCategory,
        val packageDetails: PackageDetails,
        val packageType: PackageType,
        val deliveryAddress: CustomerAddress?,
        val pickupBranch: Branch?,
        val phoneNumber: String,
        val startingDateUtc: String,
        val displayableStartingDate: String,
        val deliveryTimeSlot: DeliveryTimeSlot?,
        val selectedDays: List<DayOfWeek>,
    ) : SubscriptionRoutes(showBottomBar = false)

    @Serializable
    data class TermsAndConditions(
        val paymentUrl: String,
        val subscriptionId: String,
    ) : SubscriptionRoutes(showBottomBar = false)

    @Serializable
    data class Payment(
        val paymentUrl: String,
        val subscriptionId: String,
    ) : SubscriptionRoutes(showBottomBar = false)

}
