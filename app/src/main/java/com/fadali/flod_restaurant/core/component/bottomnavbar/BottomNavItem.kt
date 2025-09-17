package com.fadali.flod_restaurant.core.component.bottomnavbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fadali.flod_restaurant.app.navigation.graphs.Graph

data class BottomNavItem(
    val graph: Graph,
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val hasNotification: Boolean = false,
    val notificationText: String = ""
)
