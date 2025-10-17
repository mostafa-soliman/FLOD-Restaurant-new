package com.fadali.flod_restaurant.core.network.appconfig

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AppConfig(
    val otpSettings: OtpSettings,
    val timeSlotType: TimeSlotType,
    val packageType: PackageType,
) {
    companion object {
        val default = AppConfig(
            otpSettings = OtpSettings.default,
            timeSlotType = TimeSlotType.DELIVERY,
            packageType = PackageType.DELIVERY
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

@Keep
@Serializable
enum class TimeSlotType(val value: Int) {
    DELIVERY(1),
    PICKUP(2),
    BOTH(3);

    companion object {
        fun from(value: Int): TimeSlotType {
            return TimeSlotType.entries.first { it.value == value }
        }
    }
}

@Keep
@Serializable
enum class PackageType(val value: Int) {
    DELIVERY(1),
    PICKUP(2),
    BOTH(3);

    companion object {
        fun from(value: Int): PackageType {
            return PackageType.entries.first { it.value == value }
        }
    }
}

@Keep
@Serializable
enum class AddressType(val value: Int) {
    HOME(1),
    WORK(2),
    Other(3)
}
