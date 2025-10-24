package com.fadali.flod_restaurant.feature.subscription.ui.review.model

data class ConfirmationInfo(
    val mealsPerDay: Int = 0,
    val numberOfDays: Int = 0,
    val startingDate: String = "",
    val deliveryTimeSlot: String = "",
    val deliveryAddress: String = "",
    val streetName: String = "",
    val buildingNumber: String = "",
    val flatNumber: String = "",
    val phoneNumber: String = "",
    val pickUpBranchAddress: String = "",
)
