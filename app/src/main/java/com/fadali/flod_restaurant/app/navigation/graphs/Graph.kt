package com.fadali.flod_restaurant.app.navigation.graphs

import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.app.navigation.routes.BottomNavRoutes
import com.fadali.flod_restaurant.app.navigation.routes.Route
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import kotlinx.serialization.Serializable

/**
 * The parent class of all graphs in the app.
 * Each new graph must extend Graph and provide a start destination.
 *
 * Note: Any change of location or name of this class, or the Navigation package,
 * it must be reflected in the proguard rules to keep the serialization working.
 */
@Serializable
sealed class Graph(val startDestination: Route) {
    @Serializable
    data object AuthGraph : Graph(startDestination = AuthRoutes.Splash)

    @Serializable
    data object HomeGraph : Graph(startDestination = BottomNavRoutes.Home)

    @Serializable
    data object PlansGraph : Graph(startDestination = BottomNavRoutes.Plans)

    @Serializable
    data object ProfileGraph : Graph(startDestination = BottomNavRoutes.Profile)

    @Serializable
    data object MoreGraph : Graph(startDestination = BottomNavRoutes.More)

    @Serializable
    data object SubscriptionGraph : Graph(startDestination = SubscriptionRoutes.SelectPlan)

    companion object {
        /**
         * Automatic graph instance discovery using reflection
         */
        inline fun <reified T : Graph> getInstance(): T {
            return T::class.objectInstance ?: error("${T::class.simpleName} must be a data object")
        }
    }
}
