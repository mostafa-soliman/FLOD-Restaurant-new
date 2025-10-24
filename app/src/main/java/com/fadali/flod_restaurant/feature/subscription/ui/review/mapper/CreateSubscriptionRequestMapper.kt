package com.fadali.flod_restaurant.feature.subscription.ui.review.mapper

import com.fadali.flod_restaurant.core.network.appconfig.DayOfWeek
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.core.network.appconfig.PaymentMethod
import com.fadali.flod_restaurant.feature.subscription.domain.model.address.CustomerAddress
import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails
import com.fadali.flod_restaurant.feature.subscription.domain.model.payment.CreateSubscriptionRequest
import com.fadali.flod_restaurant.feature.subscription.domain.model.payment.SubscriptionItemQuantity
import com.fadali.flod_restaurant.feature.subscription.domain.model.payment.SubscriptionSchedule
import com.fadali.flod_restaurant.feature.subscription.domain.model.timeslots.DeliveryTimeSlot
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.AddonUiModel

fun getSubscriptionCreationRequest(
    packageDetails: PackageDetails,
    addons: List<AddonUiModel>,
    pickupBranch: Branch?,
    deliveryAddress: CustomerAddress?,
    startingDateUtc: String,
    paymentMethod: PaymentMethod = PaymentMethod.DIGITAL_WALLET,
    packageType: PackageType,
    promoCodeId: String?,
    selectedDays: List<DayOfWeek>,
    deliveryTimeSlot: DeliveryTimeSlot?,
    notes: String? = null
) = CreateSubscriptionRequest(
    packageId = packageDetails.id,
    itemsQuantities = addons.toSubscriptionItemQuantityList(),
    branchId = pickupBranch?.id,
    customerAddressId = deliveryAddress?.id,
    startDate = startingDateUtc,
    paymentMethod = paymentMethod.value,
    packageType = packageType.value,
    promoCodeId = promoCodeId,
    schedules = selectedDays.toSubscriptionScheduleList(deliveryTimeSlot),
    notes = notes
)

private fun List<AddonUiModel>.toSubscriptionItemQuantityList(): List<SubscriptionItemQuantity> = map {
    SubscriptionItemQuantity(
        itemId = it.id,
        quantities = it.quantity
    )
}

private fun List<DayOfWeek>.toSubscriptionScheduleList(
    deliveryTimeSlot: DeliveryTimeSlot?
): List<SubscriptionSchedule> = mapIndexed { index, day ->
    SubscriptionSchedule(
        timeSlot = deliveryTimeSlot?.id,
        dayOfWeek = day.value
    )
}
