package com.fadali.flod_restaurant.core.datastore.preferences.language

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fadali.flod_restaurant.core.datastore.AppDataStore
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import kotlinx.coroutines.flow.Flow

/**
 * DataStore for managing language preferences
 */
class LanguagePreferences(
    dispatchers: AppCoroutineDispatcher
) : AppDataStore(dispatchers) {

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("app_language")
        const val DEFAULT_LANGUAGE = "en"
    }

    /**
     * Save the selected language
     */
    suspend fun saveLanguage(context: Context, languageCode: String) {
        savePreference(context, LANGUAGE_KEY, languageCode)
    }

    /**
     * Get the current language as Flow
     */
    fun getLanguageFlow(context: Context): Flow<String> {
        return getPreferenceFlow(context, LANGUAGE_KEY, DEFAULT_LANGUAGE)
    }

    /**
     * Get the current language synchronously
     */
    suspend fun getLanguage(context: Context): String {
        return getPreference(context, LANGUAGE_KEY, DEFAULT_LANGUAGE)
    }

    /**
     * Remove language preference (will fall back to default)
     */
    suspend fun removeLanguage(context: Context) {
        removePreference(context, LANGUAGE_KEY)
    }
}
