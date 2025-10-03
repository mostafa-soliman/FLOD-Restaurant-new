package com.fadali.flod_restaurant.core.component.text.annotatedtext

import androidx.compose.ui.text.SpanStyle

object AppAnnotatedTextUtils {

    fun createTermsAndPrivacySegments(
        prefixText: String,
        termsText: String,
        middleText: String,
        privacyText: String,
        suffixText: String = "",
        onTermsClick: () -> Unit,
        onPrivacyClick: () -> Unit,
        clickableStyle: SpanStyle? = null
    ): List<ClickableTextSegment> {
        return listOf(
            ClickableTextSegment(text = prefixText),
            ClickableTextSegment(
                text = termsText,
                tag = "terms",
                style = clickableStyle,
                onClick = onTermsClick
            ),
            ClickableTextSegment(text = middleText),
            ClickableTextSegment(
                text = privacyText,
                tag = "privacy",
                style = clickableStyle,
                onClick = onPrivacyClick
            ),
            ClickableTextSegment(text = suffixText)
        )
    }

    fun createEmailSegments(
        prefixText: String,
        email: String,
        suffixText: String = "",
        onEmailClick: (String) -> Unit,
        clickableStyle: SpanStyle? = null
    ): List<ClickableTextSegment> {
        return listOf(
            ClickableTextSegment(text = prefixText),
            ClickableTextSegment(
                text = email,
                tag = "email",
                style = clickableStyle,
                onClick = { onEmailClick(email) }
            ),
            ClickableTextSegment(text = suffixText)
        )
    }

    fun createPhoneSegments(
        prefixText: String,
        phoneNumber: String,
        suffixText: String = "",
        onPhoneClick: (String) -> Unit,
        clickableStyle: SpanStyle? = null
    ): List<ClickableTextSegment> {
        return listOf(
            ClickableTextSegment(text = prefixText),
            ClickableTextSegment(
                text = phoneNumber,
                tag = "phone",
                style = clickableStyle,
                onClick = { onPhoneClick(phoneNumber) }
            ),
            ClickableTextSegment(text = suffixText)
        )
    }
}
