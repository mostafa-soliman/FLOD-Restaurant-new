package com.fadali.flod_restaurant.feature.subscription.ui.terms.utils

import com.fadali.flod_restaurant.core.util.toAssetUrl
import java.util.Locale

object SubscriptionTermsAndConditionsConstants {
    private const val TERMS_EN_FILE_NAME = "terms_and_conditions_en.html"
    private const val TERMS_AR_FILE_NAME = "terms_and_conditions_ar.html"

    val termsAssetUrl: String
        get() = if (Locale.getDefault().language.contains("ar")) {
            TERMS_AR_FILE_NAME.toAssetUrl()
        } else {
            TERMS_EN_FILE_NAME.toAssetUrl()
        }
}
