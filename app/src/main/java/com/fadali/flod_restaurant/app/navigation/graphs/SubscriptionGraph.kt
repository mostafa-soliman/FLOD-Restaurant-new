package com.fadali.flod_restaurant.app.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.fadali.flod_restaurant.app.navigation.addScreen
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressScreen
import com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui.SelectPlanScreen
import com.fadali.flod_restaurant.util.NavigationUtils.createEnumNavType
import com.fadali.flod_restaurant.util.NavigationUtils.createListNavType
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
}
