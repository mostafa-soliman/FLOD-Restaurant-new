package com.fadali.flod_restaurant.i18n


import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import kotlinx.coroutines.runBlocking

object LocaleManager {
    fun applySavedAppLanguage(context: Context) {
        val saved = runBlocking { LanguageStore.load(context) } ?: "en"
        setAppLanguage(saved, recreateActivity = false, activity = null)
    }

    fun setAppLanguage(tag: String, recreateActivity: Boolean = true, activity: Activity?) {
        val locales = LocaleListCompat.forLanguageTags(tag)
        AppCompatDelegate.setApplicationLocales(locales)
        if (recreateActivity && activity != null) {
            activity.recreate()
        }
    }
}

