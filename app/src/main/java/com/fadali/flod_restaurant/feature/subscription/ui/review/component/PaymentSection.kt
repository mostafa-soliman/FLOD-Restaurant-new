package com.fadali.flod_restaurant.feature.subscription.ui.review.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.payment.PaymentSummaryCard
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.textfield.AppTextField
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.PromoCodeValidationState
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

@Composable
fun PaymentSection(
    modifier: Modifier = Modifier,
    promoCode: String,
    onPromoCodeChange: (String) -> Unit,
    onApplyPromoCode: () -> Unit,
    planPrice: Double,
    addOnPrice: Double,
    promoDiscount: Double?,
    totalPrice: Double,
    promoCodeValidationState: PromoCodeValidationState = PromoCodeValidationState.NONE
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(R.string.payment_summary),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = promoCode,
                onValueChange = onPromoCodeChange,
                label = stringResource(R.string.promo_code),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_discount),
                        contentDescription = "Promo code icon",
                        tint = Color(0xFFA7A4A4),
                        modifier = Modifier.size(24.dp)
                    )
                },
                errorMessage = when (promoCodeValidationState) {
                    PromoCodeValidationState.EMPTY -> {
                        stringResource(R.string.promo_code_empty)
                    }
                    PromoCodeValidationState.INVALID -> {
                        stringResource(R.string.promo_code_invalid)
                    }
                    PromoCodeValidationState.VALID -> {
                        stringResource(R.string.promo_code_valid)
                    }
                    else -> {
                        null
                    }
                },
                errorMessageIcon = if (promoCodeValidationState == PromoCodeValidationState.VALID) {
                    null
                } else {
                    R.drawable.ic_error_required_icon
                },
                supportingTextColor =  if(promoCodeValidationState == PromoCodeValidationState.VALID) {
                    Color(0xFF4CAF50)
                } else {
                    MaterialTheme.colorScheme.error
                },
                trailingIcon = {
                    Button(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(end = 10.dp),
                        onClick = onApplyPromoCode,
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8E8EA9)),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        enabled = promoCodeValidationState != PromoCodeValidationState.EMPTY &&
                                promoCodeValidationState != PromoCodeValidationState.INVALID,
                    ) {
                        Text(
                            text = stringResource(R.string.apply),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 14.sp
                            ),
                        )
                    }
                }
            )
        }

        PaymentSummaryCard(
            modifier = Modifier.padding(top = 10.dp),
            planPrice = planPrice,
            addOnPrice = addOnPrice,
            promoDiscount = promoDiscount,
            totalPrice = totalPrice,
        )
    }
}

@AppPreviews
@Composable
private fun PaymentSectionPreview() {
    FLODRestaurantTheme {
        PaymentSection(
            modifier = Modifier
                .statusBarsPadding()
                .padding(20.dp),
            promoCode = "",
            onPromoCodeChange = {},
            onApplyPromoCode = {},
            planPrice = 29.99,
            addOnPrice = 9.99,
            promoDiscount = 5.00,
            totalPrice = 34.98,
            promoCodeValidationState = PromoCodeValidationState.VALID
        )
    }
}
