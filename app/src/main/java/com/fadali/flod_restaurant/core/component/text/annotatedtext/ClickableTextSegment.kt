package com.fadali.flod_restaurant.core.component.text.annotatedtext

import androidx.compose.ui.text.SpanStyle

/**
 * Represents a segment of text that can be clickable with custom styling.
 *
 * @param text The text content to display
 * @param tag Optional identifier for the segment, used for tracking clicks. Mandatory if onClick is provided
 * @param style Optional custom styling to apply to this text segment
 * @param onClick Optional click handler for this segment. If provided, the segment becomes clickable
 */
data class ClickableTextSegment(
    val text: String,
    val tag: String? = null,
    val style: SpanStyle? = null,
    val onClick: (() -> Unit)? = null
)
