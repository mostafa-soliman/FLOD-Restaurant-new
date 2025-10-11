package com.fadali.flod_restaurant.feature.subscription.domain.model.branch

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Branch(
    val id: String,
    val displayName: String,
) {
    companion object {
        val empty = Branch(
            id = "",
            displayName = "",
        )
    }
}
