package com.fadali.flod_restaurant.core.component.text.annotatedtext

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme

/**
 * This component allows you to create rich text with multiple segments where some can be clickable
 * with custom styling.
 *
 * @param segments List of text segments, where each segment can have its own styling and click behavior
 * @param modifier Modifier to be applied to the text
 * @param style Base text style to apply to the entire text
 * @param onTextClick Optional global click handler that receives the tag of the clicked segment
 *
 * @sample AppAnnotatedTextPreview
 */
@Composable
fun AppAnnotatedText(
    modifier: Modifier = Modifier,
    segments: List<ClickableTextSegment>,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    onTextClick: ((tag: String) -> Unit)? = null
) {
    val annotatedString = buildAnnotatedString {
        segments.forEach { segment ->
            if (segment.tag != null && segment.onClick != null) {
                val linkAnnotation = LinkAnnotation.Clickable(
                    tag = segment.tag,
                    styles = TextLinkStyles(
                        style = segment.style ?: SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    ),
                    linkInteractionListener = {
                        segment.onClick.invoke()
                        onTextClick?.invoke(segment.tag)
                    }
                )

                withLink(linkAnnotation) {
                    append(segment.text)
                }
            } else {
                withStyle(
                    style = segment.style ?: SpanStyle(color = style.color)
                ) {
                    append(segment.text)
                }
            }
        }
    }

    Text(
        modifier = modifier,
        text = annotatedString,
        style = style
    )
}

@Preview(showBackground = true)
@Composable
private fun AppAnnotatedTextPreview(modifier: Modifier = Modifier) {
    FLODRestaurantTheme {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppAnnotatedText(
                segments = listOf(
                    ClickableTextSegment(
                        text = "By continuing, you agree to our "
                    ),
                    ClickableTextSegment(
                        text = "Terms of Service",
                        tag = "terms",
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        ),
                        onClick = { /* Handle terms click */ }
                    )
                ),
                modifier = modifier,
                onTextClick = { tag ->
                    println("Clicked on: $tag")
                }
            )
            AppAnnotatedText(
                segments = AppAnnotatedTextUtils.createTermsAndPrivacySegments(
                    prefixText = "By continuing, you agree to our ",
                    termsText = "Terms of Service",
                    middleText = " and ",
                    privacyText = "Privacy Policy",
                    suffixText = ".",
                    onTermsClick = {},
                    onPrivacyClick = {},
                    clickableStyle = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.Underline
                    )
                )
            )
            AppAnnotatedText(
                segments = AppAnnotatedTextUtils.createEmailSegments(
                    prefixText = "Contact us at ",
                    email = "support@flodrestaurant.com",
                    suffixText = " for assistance.",
                    onEmailClick = { email ->
                        println("Email clicked: $email")
                    },
                    clickableStyle = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Medium
                    )
                )
            )
            AppAnnotatedText(
                segments = AppAnnotatedTextUtils.createPhoneSegments(
                    prefixText = "Call us at ",
                    phoneNumber = "+1 (555) 123-4567",
                    suffixText = " for reservations.",
                    onPhoneClick = { phone ->
                        println("Phone clicked: $phone")
                    }
                )
            )
        }
    }
}
