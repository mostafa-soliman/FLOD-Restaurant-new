package com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.card.AppMealPlanHorizontalCard
import com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.model.MealItem
import com.fadali.flod_restaurant.util.Dimens

@Composable
fun MealPlansSection(
    mealItems: List<MealItem>,
    modifier: Modifier = Modifier
) {
    Spacer(Modifier.height(10.dp))
    Text(
        text = stringResource(R.string.in_this_plan),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = modifier
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.CardSpacing),
        horizontalArrangement = Arrangement.spacedBy(Dimens.CardSpacing)
    ) {
        items(mealItems) { mealItem ->
            AppMealPlanHorizontalCard(
                imageUrl = mealItem.imageUrl,
                name = mealItem.name!!,
                quantity = mealItem.quantity
            )
        }
    }

}
