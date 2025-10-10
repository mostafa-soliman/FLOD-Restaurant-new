package com.fadali.flod_restaurant.core.datastore.preferences.usecase

import android.content.Context
import com.fadali.flod_restaurant.core.datastore.preferences.AppPreferences

/**
 * Use case for managing theme preferences
 */
class ThemeUseCase(
    private val appPreferences: AppPreferences
) {
    /**
     * Set the app theme mode
     */
    suspend fun setThemeMode(context: Context, themeMode: String) {
        appPreferences.saveThemeMode(context, themeMode)
    }

    /**
     * Get the current theme mode
     */
    suspend fun getThemeMode(context: Context): String {
        return appPreferences.getThemeMode(context)
    }

    /**
     * Toggle between light and dark theme
     */
    suspend fun toggleTheme(context: Context): String {
        val currentTheme = getThemeMode(context)
        val newTheme = when (currentTheme) {
            AppPreferences.THEME_LIGHT -> AppPreferences.THEME_DARK
            AppPreferences.THEME_DARK -> AppPreferences.THEME_LIGHT
            else -> AppPreferences.THEME_LIGHT // Default to light if system
        }
        setThemeMode(context, newTheme)
        return newTheme
    }
}
