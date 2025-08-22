package com.fadali.flod_restaurant.i18n

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import java.util.*

object ContextUtils {
    fun wrap(context: Context, language: String): ContextWrapper {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            config.setLayoutDirection(locale)
            val newContext = context.createConfigurationContext(config)
            return ContextWrapper(newContext)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            return ContextWrapper(context)
        }
    }
}
