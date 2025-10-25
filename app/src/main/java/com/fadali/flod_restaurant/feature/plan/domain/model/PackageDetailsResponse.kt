package com.fadali.flod_restaurant.feature.plan.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PackageDetailsResponse(
    val id: String,
    val subscriptionCode: String?,
    val subscriptionName: String?,
    val packageTitle: String?,
    val packageDescription: String?,
    val packageDaysDuration: Int?,
    val customerName: String?,
    val customerPhoneNumber: String?,
    val totalPrice: Double?,
    val discount: String?,
    val netPrice: Double?,
    val refund: Double?,
    val subscriptionStatus: Int?,
    val paymentMethod: Int?,
    val refundMethod: Int?,
    val subscriptionChannel: Int?,
    val startDate: String?,
    val endDate: String?,
    val gracePeriod: String?,
    val packageType: Int?,
    val numberOfTakenFreezes: Int?,
    val freezePeriodTaken: Int?,
    val maximumNumberAddressChanges: Int?,
    val minimumChangeAddressNoticePeriod: Int?,
    val maximumNumberTimeSlotChanges: Int?,
    val minimumChangeTimeSlotNoticePeriod: Int?,
    val maximumNumberOfPickupBranchChanges: Int?,
    val minimumChangePickupNoticePeriod: Int?,
    val creatorName: String?,
    val freeze: List<FreezeInfo>?,
    val orders: List<OrderInfo>?
)

@Keep
@Serializable
data class FreezeInfo(
    val numberOfDays: Int?,
    val startDate: String?,
    val endDate: String?,
    val note: String?
)

@Keep
@Serializable
data class OrderInfo(
    val id: String,
    val itemsQuantities: List<ItemQuantity>?,
    val branchTitle: String?,
    val customerAddress: OrderCustomerAddress?,
    val branchId: String?,
    val orderDate: String?,
    val notes: String?,
    val orderStatus: Int?,
    val orderCode: String?,
    val orderMeals: List<OrderMeal>?
)

@Keep
@Serializable
data class ItemQuantity(
    val itemId: String,
    val title: String?,
    val quantities: Int?
)

@Keep
@Serializable
data class OrderCustomerAddress(
    val id: String,
    val addressType: Int?,
    val streetName: String?,
    val buildingName: String?,
    val flatNumber: Int?,
    val city: String?,
    val district: String?,
    val landmark: String?,
    val additionalNotes: String?,
    val latitude: Double?,
    val longitude: Double?
)

@Keep
@Serializable
data class OrderMeal(
    val serialDay: Int?,
    val orderPerDay: Int?,
    val mealId: String,
    val mealTitleAr: String?,
    val mealTitleEn: String?,
    val imageUrl: String?,
    val mealNutritionInfo: MealNutritionInfo?,
    val mealSection: List<MealSection>?
)

@Keep
@Serializable
data class MealNutritionInfo(
    val calories: Int?,
    val protein: Int?,
    val fat: Int?,
    val carbs: Int?
)

@Keep
@Serializable
data class MealSection(
    val titleEn: String?,
    val titleAr: String?,
    val minimumSelection: Int?,
    val maximumSelection: Int?
)
