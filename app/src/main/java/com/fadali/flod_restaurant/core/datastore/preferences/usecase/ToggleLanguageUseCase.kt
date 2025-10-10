package com.fadali.flod_restaurant.core.datastore.preferences.usecase

import android.app.Activity
import android.content.Context
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguageManager

/**
 * Use case for toggling the app language
 * Now uses only the new DataStore system as the single source of truth
 */
class ToggleLanguageUseCase(
    private val languageManager: LanguageManager,
) {
    /**
     * Toggle between supported languages
     * @param context The context to access preferences
     * @param activity Optional activity to recreate after language change
     * @param recreateActivity Whether to recreate the activity (default: false for Compose)
     * @return The new language code
     */
    suspend operator fun invoke(
        context: Context,
        activity: Activity? = null,
        recreateActivity: Boolean = false
    ): String {
        return languageManager.toggleLanguage(context, activity, recreateActivity)
    }
}
