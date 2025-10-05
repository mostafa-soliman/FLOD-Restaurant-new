package com.fadali.flod_restaurant.core.datastore.preferences.language

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import kotlinx.coroutines.flow.Flow

/**
 * Manager for handling language preferences and app locale changes
 */
class LanguageManager(
    private val languagePreferences: LanguagePreferences,
) {

    companion object {
        const val ENGLISH = "en"
        const val ARABIC = "ar"
        
        val SUPPORTED_LANGUAGES = listOf(ENGLISH, ARABIC)
    }

    /**
     * Get the current language flow
     */
    fun getCurrentLanguageFlow(context: Context): Flow<String> {
        return languagePreferences.getLanguageFlow(context)
    }

    /**
     * Get the current language
     */
    suspend fun getCurrentLanguage(context: Context): String {
        return languagePreferences.getLanguage(context)
    }

    /**
     * Set the app language and apply it
     */
    suspend fun setLanguage(
        context: Context, 
        languageCode: String, 
        activity: Activity? = null,
        recreateActivity: Boolean = true
    ) {
        if (languageCode in SUPPORTED_LANGUAGES) {
            // Save to preferences
            languagePreferences.saveLanguage(context, languageCode)
            
            // Apply to app
            applyLanguage(languageCode, activity, recreateActivity)
        }
    }

    /**
     * Toggle between supported languages (English <-> Arabic)
     */
    suspend fun toggleLanguage(
        context: Context, 
        activity: Activity? = null,
        recreateActivity: Boolean = true
    ): String {
        val currentLanguage = getCurrentLanguage(context)
        val newLanguage = when (currentLanguage) {
            ARABIC -> ENGLISH
            else -> ARABIC
        }
        
        setLanguage(context, newLanguage, activity, recreateActivity)
        return newLanguage
    }

    /**
     * Apply the saved language from preferences
     */
    suspend fun applySavedLanguage(context: Context, activity: Activity? = null) {
        val savedLanguage = getCurrentLanguage(context)
        applyLanguage(savedLanguage, activity, recreateActivity = false)
    }

    /**
     * Apply language to the app
     */
    private fun applyLanguage(
        languageCode: String,
        activity: Activity? = null,
        recreateActivity: Boolean = true
    ) {
        val locales = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(locales)
        
        if (recreateActivity && activity != null) {
            activity.recreate()
        }
    }

    /**
     * Get the next language in the rotation
     */
    fun getNextLanguage(currentLanguage: String): String {
        return when (currentLanguage) {
            ARABIC -> ENGLISH
            else -> ARABIC
        }
    }

    /**
     * Check if the language is RTL
     */
    fun isRTL(languageCode: String): Boolean {
        return languageCode == ARABIC
    }
}
