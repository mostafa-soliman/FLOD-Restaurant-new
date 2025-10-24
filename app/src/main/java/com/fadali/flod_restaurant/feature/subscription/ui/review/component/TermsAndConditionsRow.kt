package com.fadali.flod_restaurant.feature.subscription.ui.review.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.appText

@Composable
fun TermsAndConditionsRow(
    modifier: Modifier = Modifier,
    isTermsAccepted: Boolean,
    onTermsToggled: (Boolean) -> Unit,
    onNavigateToTerms: () -> Unit
) {
    val termsClickTag = "TERMS_AND_CONDITIONS_CLICK_TAG"
    val termsText = buildAnnotatedString {
        val termsFirstText = stringResource(R.string.i_agree_to_the)
        val termsSecondText = stringResource(R.string.terms_and_conditions)
        append(termsFirstText)
        append(" ")
        withLink(
            LinkAnnotation.Clickable(
                tag = termsClickTag,
                linkInteractionListener = {
                    onNavigateToTerms()
                }
            )
        ) {
            withStyle(
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                ).toSpanStyle()
            ) {
                append(termsSecondText)
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTermsToggled(!isTermsAccepted) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isTermsAccepted,
            onCheckedChange = onTermsToggled
        )
        Text(
            text = termsText,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.appText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        )
    }
}

@AppPreviews
@Composable
private fun TermsAndConditionsRowPreview(modifier: Modifier = Modifier) {
    FLODRestaurantTheme {
        TermsAndConditionsRow(
            modifier = modifier
                .statusBarsPadding()
                .padding(20.dp),
            isTermsAccepted = false,
            onTermsToggled = {},
            onNavigateToTerms = {}
        )
    }
}
