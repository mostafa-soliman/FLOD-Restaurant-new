package com.fadali.flod_restaurant.app.navigation.graphs

import com.fadali.flod_restaurant.app.navigation.routes.Route
import com.fadali.flod_restaurant.app.navigation.routes.TempRoute
import kotlinx.serialization.Serializable

@Serializable
sealed class Graph(val startDestination: Route) {

    @Serializable
    data object Temp : Graph(startDestination = TempRoute)
}
