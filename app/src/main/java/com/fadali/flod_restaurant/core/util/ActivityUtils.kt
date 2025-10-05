package com.fadali.flod_restaurant.core.util

import android.app.Activity
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getActivity(): Activity? {
    val context = LocalContext.current
    return when (context) {
        is Activity -> context
        else -> {
            // Walk up the ContextWrapper chain to find the Activity
            var ctx = context
            while (ctx is ContextWrapper) {
                if (ctx is Activity) return ctx
                ctx = ctx.baseContext
            }
            null
        }
    }
}
