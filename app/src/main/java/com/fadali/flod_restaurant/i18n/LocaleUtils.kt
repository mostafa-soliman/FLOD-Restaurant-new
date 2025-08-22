package com.fadali.flod_restaurant.i18n

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

object LocaleUtils {

    fun getCurrentLanguage(): String {
        val current = AppCompatDelegate.getApplicationLocales()
        return current[0]?.language ?: "en"
    }

    fun setLanguage(lang: String) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(lang)
        )
    }

    fun toggled(): String {
        val next = if (getCurrentLanguage().equals("ar", ignoreCase = true)) "en" else "ar"
        setLanguage(next)
        return next
    }
}
