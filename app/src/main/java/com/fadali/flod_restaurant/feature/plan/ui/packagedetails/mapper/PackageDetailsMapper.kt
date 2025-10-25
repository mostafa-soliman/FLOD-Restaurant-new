package com.fadali.flod_restaurant.feature.plan.ui.packagedetails.mapper

import com.fadali.flod_restaurant.feature.plan.domain.model.PackageDetailsResponse
import com.fadali.flod_restaurant.feature.plan.ui.model.AddOnUiModel
import com.fadali.flod_restaurant.feature.plan.ui.model.MenuItemUiModel
import com.fadali.flod_restaurant.feature.plan.ui.model.PackageDayUiModel
import com.fadali.flod_restaurant.feature.plan.ui.model.PackageInfoUiModel
import java.text.SimpleDateFormat
import java.util.Locale

fun PackageDetailsResponse.toPackageInfo(): PackageInfoUiModel {
    val dateRange = formatDateRange(startDate, endDate)
    val daysCount = "${packageDaysDuration ?: 0} D"
    val statusLabel = getStatusLabel(subscriptionStatus)

    return PackageInfoUiModel(
        imageUrl = orders?.firstOrNull()?.orderMeals?.firstOrNull()?.imageUrl ?: "",
        title = packageTitle ?: "Package",
        dateRange = dateRange,
        daysCount = daysCount,
        statusLabel = statusLabel,
        isPickup = packageType == 2, // 1 = Delivery, 2 = Pickup
        mealsCount = "x${orders?.firstOrNull()?.orderMeals?.size ?: 0}"
    )
}

fun PackageDetailsResponse.toPackageDays(): List<PackageDayUiModel> {
    val days = mutableListOf<PackageDayUiModel>()

    orders?.forEach { order ->
        order.orderDate?.let { dateStr ->
            try {
                val inputFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                val date = inputFormat.parse(dateStr)

                date?.let {
                    val dayFormat = SimpleDateFormat("d", Locale.getDefault())
                    val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())

                    days.add(
                        PackageDayUiModel(
                            day = dayFormat.format(it),
                            month = monthFormat.format(it)
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    return days.distinctBy { "${it.day}-${it.month}" }
}

fun PackageDetailsResponse.toMenuItems(
    selectedDayIndex: Int,
    currentLanguage: String = "en"
): List<MenuItemUiModel> {
    val selectedOrder = orders?.getOrNull(selectedDayIndex) ?: return emptyList()

    return selectedOrder.orderMeals?.map { meal ->
        MenuItemUiModel(
            id = meal.mealId,
            imageUrl = meal.imageUrl ?: "",
            title = if (currentLanguage == "ar") {
                meal.mealTitleAr ?: meal.mealTitleEn ?: ""
            } else {
                meal.mealTitleEn ?: meal.mealTitleAr ?: ""
            },
            mealNumber = "Meal No (${meal.orderPerDay ?: 0})",
            calories = meal.mealNutritionInfo?.calories
        )
    } ?: emptyList()
}

fun PackageDetailsResponse.toAddOns(): List<AddOnUiModel> {
    val addOns = mutableListOf<AddOnUiModel>()

    orders?.firstOrNull()?.itemsQuantities?.forEach { item ->
        addOns.add(
            AddOnUiModel(
                id = item.itemId,
                imageUrl = "",
                name = item.title ?: "",
                calories = null,
                price = 0.0,
                quantity = item.quantities ?: 0
            )
        )
    }

    return addOns
}

private fun formatDateRange(startDate: String?, endDate: String?): String {
    if (startDate == null || endDate == null) return ""

    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

        val start = inputFormat.parse(startDate)
        val end = inputFormat.parse(endDate)

        if (start != null && end != null) {
            "${outputFormat.format(start)} - ${outputFormat.format(end)}"
        } else {
            ""
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

private fun getStatusLabel(status: Int?): String {
    return when (status) {
        1 -> "Active"
        2 -> "Paused"
        3 -> "Completed"
        4 -> "Cancelled"
        else -> "Unknown"
    }
}
