package com.fadali.flod_restaurant.feature.home.ui.model

import androidx.annotation.StringRes

data class ServicesUiModel(
    val imageModel: Any,
    @StringRes val title: Int,
    @StringRes val actionText: Int,
    val serviceId: String
)