package com.fadali.flod_restaurant.feature.subscription.domain.model.branch

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Branch(
    val id: String,
    val displayName: String,
    val addressLat: Double,
    val addressLng: Double,
) {
    companion object {
        val empty = Branch(
            id = "",
            displayName = "",
            addressLat = 0.0,
            addressLng = 0.0,
        )
    }
}
