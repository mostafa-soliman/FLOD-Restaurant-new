package com.fadali.flod_restaurant.app.navigation.routes

import kotlinx.serialization.Serializable

/**
 * The parent class for all routes in the app.
 *
 * Note: Any change of location or name of this class, or the Navigation package,
 * it must be reflected in the proguard rules to keep the serialization working.
 * @param showBottomNavBar A boolean indicating whether the bottom navigation bar should be shown for this route.
 * */
@Serializable
sealed class Route(val showBottomNavBar: Boolean)
