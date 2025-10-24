package com.fadali.flod_restaurant.core.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.image.AppNetworkImage
import com.fadali.flod_restaurant.core.component.image.SaudiRiyalIcon
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.nutrientColors

data class PlanPackage(
    val title: String,
    val description: String,
    val calories: Int?,
    val price: Double,
    val image: String
)

@Composable
fun AppPackageCard(
    planPackage: PlanPackage,
    onBtnClicked: () -> Unit,
    onCardClicked: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(6.dp)
    Surface(
        onClick = onCardClicked,
        shape = shape,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier
            .width(280.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = shape
            )
    ) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape)
            ) {
                AppNetworkImage(
                    model = planPackage.image,
                    modifier = Modifier.fillMaxSize(),
                    shape = shape
                )
                planPackage.calories?.let {
                    Box(
                        Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color(0x9C000000))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Text(
                            text = stringResource(R.string.calories_badge, planPackage.calories),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.nutrientColors.calories,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                        )
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 11.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1.7f),
                    text = planPackage.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                )
                Row(
                    Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    SaudiRiyalIcon(
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = stringResource(R.string.price_sar, planPackage.price),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = planPackage.description,
                style = MaterialTheme.typography.bodySmall,
                color = colorScheme.onSurface.copy(alpha = .7f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 13.dp)
                        .heightIn(min = 30.dp),
                    onClick = onBtnClicked,
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 10.dp,
                        vertical = 4.dp,
                    )
                ) {
                    Text(
                        text = stringResource(R.string.package_card_primary_action),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                    )
                }
            }
        }
    }
}

@AppPreviews
@Composable
private fun PackageCardPreview() {
    FLODRestaurantTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppPackageCard(
                planPackage = PlanPackage(
                    title = "VARIETY PACKAGE VARIETY PACKAGE ",
                    description = "DescriptionDescriptionDescriptionDescription",
                    calories = 200,
                    price = 55244.9,
                    image = ""
                ),
                onBtnClicked = {},
                onCardClicked = {}
            )
        }
    }
}
