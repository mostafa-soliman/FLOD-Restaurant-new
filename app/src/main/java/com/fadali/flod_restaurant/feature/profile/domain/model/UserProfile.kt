package com.fadali.flod_restaurant.feature.profile.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val gender: Int,
    val age: Int,
    val bmr: Double?,
    val activityTitle: String?,
    val goalTitle: String?,
    val height: Double?,
    val weight: Double?,
    val allergies: List<String>?,
)
