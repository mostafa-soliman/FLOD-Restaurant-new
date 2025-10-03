package com.fadali.flod_restaurant.core.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Sealed class to represent either a string value or a string resource ID
 */
sealed class DeferredString {
    data class StringValue(val value: String) : DeferredString()
    data class StringResource(@StringRes val resId: Int) : DeferredString()
}

/**
 * Extension function to resolve DeferredString to actual string
 */
fun DeferredString.resolve(context: Context): String {
    return when (this) {
        is DeferredString.StringValue -> this.value
        is DeferredString.StringResource -> context.getString(this.resId)
    }
}

/**
 * Extension function to resolve DeferredString to actual string with format arguments
 */
fun DeferredString.resolve(context: Context, vararg formatArgs: Any): String {
    return when (this) {
        is DeferredString.StringValue -> String.format(this.value, *formatArgs)
        is DeferredString.StringResource -> context.getString(this.resId, *formatArgs)
    }
}

/**
 * Composable function to resolve DeferredString in Compose
 */
@Composable
fun DeferredString.resolve(): String {
    return when (this) {
        is DeferredString.StringValue -> this.value
        is DeferredString.StringResource -> stringResource(this.resId)
    }
}

/**
 * Composable function to resolve DeferredString with format arguments in Compose
 */
@Composable
fun DeferredString.resolve(vararg formatArgs: Any): String {
    return when (this) {
        is DeferredString.StringValue -> String.format(this.value, *formatArgs)
        is DeferredString.StringResource -> stringResource(this.resId, *formatArgs)
    }
}

/**
 * Helper function to create DeferredString from string
 */
fun String.toDeferredString(): DeferredString = DeferredString.StringValue(this)

/**
 * Helper function to create DeferredString from string resource ID
 */
fun @receiver:StringRes Int.toDeferredString(): DeferredString = DeferredString.StringResource(this)

/**
 * Extension function to check if DeferredString is empty after resolution
 */
fun DeferredString.isEmpty(context: Context): Boolean {
    return this.resolve(context).isEmpty()
}

/**
 * Extension function to check if DeferredString is blank after resolution
 */
fun DeferredString.isBlank(context: Context): Boolean {
    return this.resolve(context).isBlank()
}

/**
 * Extension function to check if DeferredString is not blank or empty after resolution
 */
fun DeferredString.isNotBlankOrEmpty(context: Context): Boolean {
    val resolved = this.resolve(context)
    return resolved.isNotEmpty() && resolved.isNotBlank()
}
