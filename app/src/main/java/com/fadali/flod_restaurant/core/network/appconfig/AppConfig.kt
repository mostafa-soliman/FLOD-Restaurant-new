package com.fadali.flod_restaurant.core.network.appconfig

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AppConfig(
    val otpSettings: OtpSettings
) {
    companion object {
        val default = AppConfig(
            otpSettings = OtpSettings.default
        )
    }
}

@Keep
@Serializable
data class OtpSettings(
    val expireByMinutes: Int,
    val resendTrialsCount: Int,
    val resendTrialsBlockInMinutes: Int,
    val wrongOTPTrailsCount: Int,
    val wrongOTPTrailsBlockInMinutes: Int,
    val resendTrialsExpireInMinutes: Int,
    val wrongOTPTrailsExpireInMinutes: Int,
) {
    companion object {
        val default = OtpSettings(
            expireByMinutes = 3,
            resendTrialsCount = 3,
            resendTrialsBlockInMinutes = 15,
            wrongOTPTrailsCount = 3,
            wrongOTPTrailsBlockInMinutes = 15,
            resendTrialsExpireInMinutes = 15,
            wrongOTPTrailsExpireInMinutes = 15,
        )
    }
}
