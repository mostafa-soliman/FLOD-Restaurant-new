package com.fadali.flod_restaurant

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.fadali.flod_restaurant.data.LanguageDataStore
import com.fadali.flod_restaurant.i18n.LanguageStore
import com.fadali.flod_restaurant.i18n.LocaleManager
import com.fadali.flod_restaurant.i18n.LocaleUtils
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //  Light
        LocaleManager.applySavedAppLanguage(this)
        val savedLang = runBlocking { LanguageStore.load(this@MainApplication) } ?: "en"
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(savedLang))
    }
}
