package com.fadali.flod_restaurant

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.fadali.flod_restaurant.data.LanguageDataStore
import kotlinx.coroutines.runBlocking

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //  Light

        val savedTag = runBlocking { LanguageDataStore.getLanguage(this@MainApplication) }

        val locales = if (savedTag.isNullOrBlank()) {
            LocaleListCompat.create()
        } else {
            LocaleListCompat.forLanguageTags(savedTag)
        }

        AppCompatDelegate.setApplicationLocales(locales)
    }
}
