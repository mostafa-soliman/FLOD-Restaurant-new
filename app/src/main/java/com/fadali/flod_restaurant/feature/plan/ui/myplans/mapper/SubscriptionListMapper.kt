package com.fadali.flod_restaurant.feature.plan.ui.myplans.mapper

import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.feature.plan.domain.model.SubscriptionListItem
import com.fadali.flod_restaurant.feature.plan.ui.myplans.model.PlanItem
import com.fadali.flod_restaurant.feature.plan.ui.myplans.model.PlanStatus
import java.text.SimpleDateFormat
import java.util.Locale

fun SubscriptionListItem.toPlanItem(): PlanItem {
    val status = when (this.subscriptionStatus) {
        1 -> PlanStatus.ACTIVE
        2 -> PlanStatus.EXPIRED
        3 -> PlanStatus.CANCELLED
        else -> PlanStatus.ACTIVE
    }

    val isPickup = this.packageType == PackageType.PICKUP.value

    val planDuration = "${this.packageDaysDuration ?: 0} Days plan"
    val mealsCount = "x${this.mealsPerDay ?: 0} Meals"

    val startDateFormatted = formatDate(this.startDate)
    val endDateFormatted = formatDate(this.endDate)

    return PlanItem(
        id = this.id,
        imageUrl = this.packageImageUrl ?: "",
        title = this.packageTitle ?: "",
        planDuration = planDuration,
        mealsCount = mealsCount,
        startDate = if (status == PlanStatus.ACTIVE) "Start: $startDateFormatted" else "",
        endDate = endDateFormatted,
        isPickup = isPickup,
        status = status
    )
}

private fun formatDate(dateString: String?): String {
    if (dateString.isNullOrEmpty()) return ""
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: ""
    } catch (e: Exception) {
        ""
    }
}
