package com.fadali.flod_restaurant.feature.subscription.selectplan.ui.mapper

import com.fadali.flod_restaurant.core.component.card.PlanPackage
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails

fun PackageDetails.toPlanPackage() = PlanPackage(
    title = title,
    description = description,
    price = price,
    image = imageUrl ?: "",
    calories = 200
)
