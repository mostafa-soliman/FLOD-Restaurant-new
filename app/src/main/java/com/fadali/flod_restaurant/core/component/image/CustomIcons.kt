package com.fadali.flod_restaurant.core.component.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.fadali.flod_restaurant.R

object CustomIcons {
    val Pickup: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.ic_pickup)
    val Delivery: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.ic_delivery)
}