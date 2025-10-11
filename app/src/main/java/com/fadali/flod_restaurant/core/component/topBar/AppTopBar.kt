package com.fadali.flod_restaurant.core.component.topBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.util.Dimens

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    showBackButton: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    backButtonTint: Color = Color.Unspecified
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackButton) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = stringResource(R.string.nav_back),
                tint = backButtonTint,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onBackClick?.invoke()
                    }
            )
        } else {
            Spacer(
                modifier = Modifier
                    .size(Dimens.MediumIcon)
                    .align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight(600),
                color = textColor,
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = null,
            tint = Color.Transparent,
            modifier = Modifier
                .size(Dimens.MediumIcon)
                .align(Alignment.CenterVertically)
        )
    }
}

/* ========================= PREVIEWS ========================= */

@Preview(name = "Core Generic Top Bar - English")
@Composable
private fun CoreGenericTopBarPreviewEnglish() {
    FLODRestaurantTheme {
        AppTopBar(
            title = "Pickup",
            modifier = Modifier
        )
    }
}

@Preview(name = "Core Generic Top Bar - Arabic", locale = "ar")
@Composable
private fun CoreGenericTopBarPreviewArabic() {
    FLODRestaurantTheme {
        AppTopBar(
            title = "الاستلام",
            modifier = Modifier
        )
    }
}

@Preview(name = "Core Generic Top Bar - No Back Button")
@Composable
private fun CoreGenericTopBarPreviewNoBackButton() {
    FLODRestaurantTheme {
        AppTopBar(
            title = "Home",
            showBackButton = false,
            modifier = Modifier
        )
    }
}
