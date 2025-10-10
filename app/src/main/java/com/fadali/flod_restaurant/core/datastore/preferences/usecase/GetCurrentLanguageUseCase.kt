package com.fadali.flod_restaurant.core.datastore.preferences.usecase

import android.content.Context
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguageManager
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting the current app language
 */
class GetCurrentLanguageUseCase(
    private val languageManager: LanguageManager
) {
    /**
     * Get the current language as Flow
     */
    fun getLanguageFlow(context: Context): Flow<String> {
        return languageManager.getCurrentLanguageFlow(context)
    }

    /**
     * Get the current language synchronously
     */
    suspend fun getCurrentLanguage(context: Context): String {
        return languageManager.getCurrentLanguage(context)
    }
}
