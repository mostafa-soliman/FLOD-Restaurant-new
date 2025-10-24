package com.fadali.flod_restaurant.core.network.appconfig

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.fadali.flod_restaurant.R
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AppConfig(
    val otpSettings: OtpSettings,
    val timeSlotType: TimeSlotType,
    val packageType: PackageType,
    val daysOfWeek: List<DayOfWeek>,
    val daysOff: List<DayOfWeek>,
    val availableDaysOfWeek: List<DayOfWeek> = daysOfWeek - daysOff,
) {
    companion object {
        val default = AppConfig(
            otpSettings = OtpSettings.default,
            timeSlotType = TimeSlotType.DELIVERY,
            packageType = PackageType.DELIVERY,
            daysOfWeek = DayOfWeek.entries,
            daysOff = listOf(DayOfWeek.FRIDAY)
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

        fun PackageType.toTimeSlotType(): TimeSlotType {
            return when (this) {
                DELIVERY -> TimeSlotType.DELIVERY
                PICKUP -> TimeSlotType.PICKUP
                BOTH -> TimeSlotType.BOTH
            }
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

@Keep
@Serializable
enum class DayOfWeek(
    val value: Int,
    @StringRes val displayName: Int
) {
    SATURDAY(
        value = 6,
        displayName = R.string.saturday
    ),
    SUNDAY(
        value = 0,
        displayName = R.string.sunday
    ),
    MONDAY(
        value = 1,
        displayName = R.string.monday
    ),
    TUESDAY(
        value = 2,
        displayName = R.string.tuesday
    ),
    WEDNESDAY(
        value = 3,
        displayName = R.string.wednesday
    ),
    THURSDAY(
        value = 4,
        displayName = R.string.thursday
    ),
    FRIDAY(
        value = 5,
        displayName = R.string.friday
    )
}

@Keep
@Serializable
enum class PaymentMethod(val value: Int) {
    CASH(1),
    POS(2),
    BANK_TRANSFER(3),
    DIGITAL_WALLET(4);
}
