package com.fadali.flod_restaurant.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.fadali.flod_restaurant.app.di.appModule
import com.fadali.flod_restaurant.i18n.LanguageStore
import com.fadali.flod_restaurant.i18n.LocaleManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //  Light
        LocaleManager.applySavedAppLanguage(this)
        val savedLang = runBlocking { LanguageStore.load(this@MainApplication) } ?: "en"
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(savedLang))
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}
