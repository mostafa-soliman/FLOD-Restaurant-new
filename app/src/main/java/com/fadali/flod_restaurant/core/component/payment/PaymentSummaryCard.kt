package com.fadali.flod_restaurant.core.component.payment

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.image.SaudiRiyalIcon
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

data class PaymentItem(
    val label: String,
    val price: Double,
    val isDiscount: Boolean = false
)

@Composable
fun PaymentSummaryCard(
    modifier: Modifier = Modifier,
    planPrice: Double,
    addOnPrice: Double,
    promoDiscount: Double?,
    totalPrice: Double,
) {
    val paymentItems = listOfNotNull(
        PaymentItem(label = stringResource(R.string.plan_price), price = planPrice),
        PaymentItem(label = stringResource(R.string.add_on_price), price = addOnPrice),
        promoDiscount?.let {
            PaymentItem(
                label = stringResource(R.string.promo_code),
                price = it,
                isDiscount = true
            )
        },
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Column {
            paymentItems.forEach { item ->
                PaymentRow(
                    label = item.label,
                    price = if (item.isDiscount) -item.price else item.price,
                    isTotal = false,
                    isDiscount = item.isDiscount
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            DashedDivider(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PaymentRow(
                label = stringResource(R.string.plan_price),
                price = totalPrice,
                isTotal = true,
                isDiscount = false
            )
        }
    }
}

@Composable
private fun DashedDivider(
    modifier: Modifier = Modifier,
    color: Color = Color(0xB2000000),
    thickness: Float = 1f
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness.dp)
    ) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        drawLine(
            color = color,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = thickness,
            pathEffect = pathEffect
        )
    }
}

@Composable
private fun PaymentRow(
    label: String,
    price: Double,
    isTotal: Boolean = false,
    isDiscount: Boolean = false
) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (isTotal) colorScheme.appText else MaterialTheme.colorScheme.appText.copy(
                    alpha = 0.6f
                ),
                fontWeight = if (isTotal) FontWeight.Medium else FontWeight.SemiBold,
                fontSize = 18.sp,
            )
        )
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SaudiRiyalIcon(
                    modifier = Modifier.sizeIn(
                        minWidth = if (isTotal) 16.dp else 14.dp,
                        minHeight = if (isTotal) 16.dp else 14.dp
                    ),
                    tint = if (isTotal) colorScheme.primary else colorScheme.appText
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (price < 0) "-${kotlin.math.abs(price)}" else price.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = colorScheme.appText,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    )
                )
            }
        }
    }
}

@AppPreviews
@Composable
private fun PaymentSummaryCardPreview() {
    FLODRestaurantTheme {
        PaymentSummaryCard(
            modifier = Modifier
                .statusBarsPadding()
                .padding(20.dp),
            planPrice = 100.0,
            addOnPrice = 25.0,
            promoDiscount = 15.0,
            totalPrice = 110.0
        )
    }
}
