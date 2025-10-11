package com.fadali.flod_restaurant.feature.subscription.domain.model.category

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ProductCategory(
    val id: String,
    val displayName: String,
    val imageUrl: String? = null, // Fixme: It's not in the API response
) {
    companion object {
        val empty = ProductCategory(
            id = "",
            displayName = "",
            imageUrl = null,
        )
    }
}
