package com.fadali.flod_restaurant.feature.profile.data.mapper

import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.feature.profile.data.model.ActivitiesLookupResponseDataModel
import com.fadali.flod_restaurant.feature.profile.data.model.ActivityDataDataModel
import com.fadali.flod_restaurant.feature.profile.data.model.GoalDataDataModel
import com.fadali.flod_restaurant.feature.profile.data.model.GoalsLookupResponseDataModel
import com.fadali.flod_restaurant.feature.profile.domain.model.ActivitiesLookupResponse
import com.fadali.flod_restaurant.feature.profile.domain.model.ActivityData
import com.fadali.flod_restaurant.feature.profile.domain.model.GoalData
import com.fadali.flod_restaurant.feature.profile.domain.model.GoalsLookupResponse

fun ActivitiesLookupResponseDataModel.toDomainModel() = ActivitiesLookupResponse(
    items = items.map { it.toDomainModel() },
    totalCount = totalCount,
)

fun ActivityDataDataModel.toDomainModel() = ActivityData(
    id = id,
    displayName = displayName,
    imageUrl = "${BuildConfig.RESOURCES_BASE_URL}$imageUrl",
)

fun GoalsLookupResponseDataModel.toDomainModel() = GoalsLookupResponse(
    items = items.map { it.toDomainModel() },
    totalCount = totalCount,
)

fun GoalDataDataModel.toDomainModel() = GoalData(
    id = id,
    displayName = displayName,
    imageUrl = "${BuildConfig.RESOURCES_BASE_URL}$imageUrl",
)
