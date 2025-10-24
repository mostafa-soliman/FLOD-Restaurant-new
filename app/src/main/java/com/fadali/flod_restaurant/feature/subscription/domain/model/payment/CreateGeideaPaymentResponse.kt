package com.fadali.flod_restaurant.feature.subscription.domain.model.payment

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CreateGeideaPaymentResponse(
    val transactionId: String?,
    val geideaOrderId: String?,
    val paymentUrl: String,
    val status: String?,
    val message: String?,
    val isSuccess: Boolean?,
    val errorCode: String?,
    val errorMessage: String?,
)
