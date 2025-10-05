package com.fadali.flod_restaurant.feature.profile.data.mapper

import com.fadali.flod_restaurant.BuildConfig
import com.fadali.flod_restaurant.feature.profile.data.model.ActivitiesLookupResponse
import com.fadali.flod_restaurant.feature.profile.data.model.ActivityDataModel
import com.fadali.flod_restaurant.feature.profile.data.model.AllergiesDataModel
import com.fadali.flod_restaurant.feature.profile.data.model.AllergiesLookupResponse
import com.fadali.flod_restaurant.feature.profile.data.model.GoalDataModel
import com.fadali.flod_restaurant.feature.profile.data.model.GoalsLookupResponse
import com.fadali.flod_restaurant.feature.profile.domain.model.ActivitiesLookupModel
import com.fadali.flod_restaurant.feature.profile.domain.model.ActivityData
import com.fadali.flod_restaurant.feature.profile.domain.model.AllergyData
import com.fadali.flod_restaurant.feature.profile.domain.model.AllergiesLookupModel
import com.fadali.flod_restaurant.feature.profile.domain.model.GoalData
import com.fadali.flod_restaurant.feature.profile.domain.model.GoalsLookupModel

fun ActivitiesLookupResponse.toDomainModel() = ActivitiesLookupModel(
    items = items.map { it.toDomainModel() },
    totalCount = totalCount,
)

fun ActivityDataModel.toDomainModel() = ActivityData(
    id = id,
    displayName = displayName,
    imageUrl = "${BuildConfig.RESOURCES_BASE_URL}$imageUrl",
)

fun GoalsLookupResponse.toDomainModel() = GoalsLookupModel(
    items = items.map { it.toDomainModel() },
    totalCount = totalCount,
)

fun GoalDataModel.toDomainModel() = GoalData(
    id = id,
    displayName = displayName,
    imageUrl = "${BuildConfig.RESOURCES_BASE_URL}$imageUrl",
)

fun AllergiesLookupResponse.toDomainModel() = AllergiesLookupModel(
    items = items.map { it.toDomainModel() },
    totalCount = totalCount,
)

fun AllergiesDataModel.toDomainModel() = AllergyData(
    id = id,
    displayName = displayName,
    imageUrl = "${BuildConfig.RESOURCES_BASE_URL}$imageUrl",
)
