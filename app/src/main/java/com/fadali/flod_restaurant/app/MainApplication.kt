package com.fadali.flod_restaurant.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.fadali.flod_restaurant.app.di.appModule
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguageManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.logger.Level

@HiltAndroidApp
class MainApplication : Application(), KoinComponent {

    private val languageManager: LanguageManager by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //  Light

        initializeLanguage()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    private fun initializeLanguage() {
        runBlocking {
            try {
                // Apply the saved language from DataStore
                languageManager.applySavedLanguage(this@MainApplication)
            } catch (e: Exception) {
                // If something goes wrong, fallback to default
                // Do nothing - let system handle default language
            }
        }
    }
}
