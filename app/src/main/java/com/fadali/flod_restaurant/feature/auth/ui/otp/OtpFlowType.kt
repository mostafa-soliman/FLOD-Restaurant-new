package com.fadali.flod_restaurant.feature.auth.ui.otp

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
enum class OtpFlowType {
    LOGIN,
    SIGN_UP
}
