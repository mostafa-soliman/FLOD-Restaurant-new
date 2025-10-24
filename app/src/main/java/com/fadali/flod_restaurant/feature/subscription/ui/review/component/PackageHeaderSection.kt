package com.fadali.flod_restaurant.feature.subscription.ui.review.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.image.AppNetworkImage
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.NutritionInfo
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetailsBranch
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

@Composable
fun PackageHeaderSection(
    modifier: Modifier = Modifier,
    packageDetails: PackageDetails?,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFECECEC),
                shape = RoundedCornerShape(size = 15.dp)
            )
            .padding(vertical = 12.dp, horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppNetworkImage(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .clip(CircleShape),
            model = packageDetails?.imageUrl ?: "",
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.padding(start = 11.dp)
        ) {
            Text(
                text = packageDetails?.title?.uppercase() ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.appText,
                )
            )
            Text(
                modifier = Modifier.padding(
                    start = 5.dp,
                    top = 10.dp
                ),
                text = stringResource(R.string.subscription_meal_text, packageDetails?.mealsPerDay ?: ""),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                )
            )
        }
    }
}

@AppPreviews
@Composable
private fun PackageHeaderSectionPreview() {
    FLODRestaurantTheme {
        PackageHeaderSection(
            modifier = Modifier
                .statusBarsPadding()
                .padding(20.dp),
            packageDetails = PackageDetails(
                id = "1",
                title = "Chicken Plan",
                description = "A balanced meal plan for everyday health.",
                price = 199.99,
                durationDays = 30,
                mealsPerDay = 3,
                imageUrl = "",
                nutritionInfo = NutritionInfo(
                    id = "1",
                    title = "",
                    calories = 1500,
                    protein = 100,
                    carbohydrates = 150,
                    fats = 50
                ),
                branches = listOf(
                    PackageDetailsBranch(
                        id = "",
                        title = "Olaya Branch",
                        deliveryArea = ""
                    )
                )
            )
        )
    }
}
