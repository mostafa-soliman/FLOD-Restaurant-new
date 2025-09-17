package com.fadali.flod_restaurant.core.component.bottomnavbar

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.graphs.Graph

/**
 * Configuration object for bottom navigation items
 */
object BottomNavConfig {
    val items = listOf(
        BottomNavItem(
            graph = Graph.HomeGraph,
            icon = R.drawable.ic_bottom_nav_home,
            label = R.string.nav_home
        ),
        BottomNavItem(
            graph = Graph.PlansGraph,
            icon = R.drawable.ic_bottom_nav_plans,
            label = R.string.nav_plans
        ),
        BottomNavItem(
            graph = Graph.ProfileGraph,
            icon = R.drawable.ic_bottom_nav_profile,
            label = R.string.nav_profile
        ),
        BottomNavItem(
            graph = Graph.MoreGraph,
            icon = R.drawable.ic_bottom_nav_more,
            label = R.string.nav_more
        )
    )
}
