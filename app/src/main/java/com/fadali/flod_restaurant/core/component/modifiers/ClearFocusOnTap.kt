package com.fadali.flod_restaurant.core.component.modifiers

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

/**
 * A modifier that clears focus when the user taps on the element.
 * This is useful for dismissing the keyboard when tapping outside text fields.
 *
 * @param focusManager The FocusManager to use for clearing focus. If null, will use LocalFocusManager.current when applied
 */
fun Modifier.clearFocusOnTap(
    focusManager: FocusManager? = null
): Modifier = if (focusManager != null) {
    this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus()
        })
    }
} else {
    // Only use composed when we need to access LocalFocusManager.current
    @SuppressLint("UnnecessaryComposedModifier")
    composed {
        val actualFocusManager = LocalFocusManager.current
        this.pointerInput(Unit) {
            detectTapGestures(onTap = {
                actualFocusManager.clearFocus()
            })
        }
    }
}
