package com.fadali.flod_restaurant.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.fadali.flod_restaurant.app.navigation.addScreen
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.network.appconfig.DayOfWeek
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageMeal
import com.fadali.flod_restaurant.feature.subscription.domain.model.timeslots.DeliveryTimeSlot
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressScreen
import com.fadali.flod_restaurant.feature.subscription.ui.payment.PaymentScreen
import com.fadali.flod_restaurant.feature.subscription.ui.planmenu.PlanMenuScreen
import com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate.PlanStartingDateScreen
import com.fadali.flod_restaurant.feature.subscription.ui.review.ReviewScreen
import com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui.SelectPlanScreen
import com.fadali.flod_restaurant.feature.subscription.ui.terms.SubscriptionTermsAndConditionsScreen
import com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.ViewPackageScreen
import com.fadali.flod_restaurant.util.NavigationUtils.createEnumNavType
import com.fadali.flod_restaurant.util.NavigationUtils.createListNavType
import com.fadali.flod_restaurant.util.NavigationUtils.createNavType
import com.fadali.flod_restaurant.util.NavigationUtils.createNullableNavType
import kotlin.reflect.typeOf

fun NavGraphBuilder.subscriptionGraph(navController: NavHostController) {
    addScreen<SubscriptionRoutes.SelectPlan> {
        SelectPlanScreen(navController)
    }

    addScreen<SubscriptionRoutes.BranchAddress>(
        typeMap = mapOf(
            typeOf<PackageType>() to createEnumNavType<PackageType>(),
            typeOf<List<CustomerAddress>>() to createListNavType<CustomerAddress>(),
            typeOf<List<Branch>>() to createListNavType<Branch>(),
        )
    ) { navBackStackEntry ->
        val args = navBackStackEntry.toRoute<SubscriptionRoutes.BranchAddress>()
        BranchAddressScreen(
            navController = navController,
            args = args
        )
    }

    addScreen<SubscriptionRoutes.ViewPackage>(
        typeMap = mapOf(
            typeOf<PackageDetails>() to createNavType<PackageDetails>(),
            typeOf<ProductCategory>() to createNavType<ProductCategory>(),
            typeOf<CustomerAddress?>() to createNullableNavType<CustomerAddress>(),
            typeOf<Branch?>() to createNullableNavType<Branch>()
        )
    ) { navBackStackEntry ->
        val args = navBackStackEntry.toRoute<SubscriptionRoutes.ViewPackage>()
        ViewPackageScreen(
            navController = navController,
            args = args
        )
    }

    addScreen<SubscriptionRoutes.PlanMenu>(
        typeMap = mapOf(
            typeOf<List<PackageMeal>>() to createListNavType<PackageMeal>(),
        )
    ) { navBackStackEntry ->
        val args = navBackStackEntry.toRoute<SubscriptionRoutes.PlanMenu>()
        PlanMenuScreen(
            navController = navController,
            args = args
        )
    }

    addScreen<SubscriptionRoutes.PlanStartingDate>(
        typeMap = mapOf(
            typeOf<ProductCategory>() to createNavType<ProductCategory>(),
            typeOf<PackageDetails>() to createNavType<PackageDetails>(),
            typeOf<PackageType>() to createEnumNavType<PackageType>(),
            typeOf<CustomerAddress?>() to createNullableNavType<CustomerAddress>(),
            typeOf<Branch?>() to createNullableNavType<Branch>(),
        )
    ) { navBackStackEntry ->
        val args = navBackStackEntry.toRoute<SubscriptionRoutes.PlanStartingDate>()
        PlanStartingDateScreen(
            navController = navController,
            args = args
        )
    }

    addScreen<SubscriptionRoutes.SubscriptionReview>(
        typeMap = mapOf(
            typeOf<ProductCategory>() to createNavType<ProductCategory>(),
            typeOf<PackageDetails>() to createNavType<PackageDetails>(),
            typeOf<PackageType>() to createEnumNavType<PackageType>(),
            typeOf<CustomerAddress?>() to createNullableNavType<CustomerAddress>(),
            typeOf<Branch?>() to createNullableNavType<Branch>(),
            typeOf<DeliveryTimeSlot?>() to createNullableNavType<DeliveryTimeSlot>(),
            typeOf<DayOfWeek>() to createListNavType<DayOfWeek>(),
        )
    ) { navBackStackEntry ->
        val args = navBackStackEntry.toRoute<SubscriptionRoutes.SubscriptionReview>()
        ReviewScreen(
            navController = navController,
            args = args
        )
    }

    addScreen<SubscriptionRoutes.TermsAndConditions> { navBackStackEntry ->
        val args = navBackStackEntry.toRoute<SubscriptionRoutes.TermsAndConditions>()
        SubscriptionTermsAndConditionsScreen(
            navController = navController,
            args = args
        )
    }

    addScreen<SubscriptionRoutes.Payment> { navBackStackEntry ->
        val args = navBackStackEntry.toRoute<SubscriptionRoutes.Payment>()
        PaymentScreen(
            navController = navController,
            args = args
        )
    }

}
