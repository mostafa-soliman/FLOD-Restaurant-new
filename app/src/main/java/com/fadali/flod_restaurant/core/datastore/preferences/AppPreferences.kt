package com.fadali.flod_restaurant.core.datastore.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fadali.flod_restaurant.core.datastore.AppDataStore
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import kotlinx.coroutines.flow.Flow

/**
 * DataStore for managing general app preferences
 * This class handles app-wide settings that are not domain-specific
 */
class AppPreferences(
    dispatchers: AppCoroutineDispatcher
) : AppDataStore(dispatchers) {

    companion object {
        // Theme preferences
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
        const val THEME_SYSTEM = "system"
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"

        // Notification preferences
        private val NOTIFICATIONS_ENABLED_KEY = booleanPreferencesKey("notifications_enabled")
        private val PUSH_NOTIFICATIONS_KEY = booleanPreferencesKey("push_notifications")

        // Onboarding preferences
        private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
        private val FIRST_TIME_USER_KEY = booleanPreferencesKey("first_time_user")

        // App version tracking
        private val LAST_APP_VERSION_KEY = stringPreferencesKey("last_app_version")
    }

    // Theme preferences
    suspend fun saveThemeMode(context: Context, themeMode: String) {
        savePreference(context, THEME_MODE_KEY, themeMode)
    }

    fun getThemeModeFlow(context: Context): Flow<String> {
        return getPreferenceFlow(context, THEME_MODE_KEY, THEME_SYSTEM)
    }

    suspend fun getThemeMode(context: Context): String {
        return getPreference(context, THEME_MODE_KEY, THEME_SYSTEM)
    }

    // Notification preferences
    suspend fun setNotificationsEnabled(context: Context, enabled: Boolean) {
        savePreference(context, NOTIFICATIONS_ENABLED_KEY, enabled)
    }

    fun getNotificationsEnabledFlow(context: Context): Flow<Boolean> {
        return getPreferenceFlow(context, NOTIFICATIONS_ENABLED_KEY, true)
    }

    suspend fun areNotificationsEnabled(context: Context): Boolean {
        return getPreference(context, NOTIFICATIONS_ENABLED_KEY, true)
    }

    suspend fun setPushNotificationsEnabled(context: Context, enabled: Boolean) {
        savePreference(context, PUSH_NOTIFICATIONS_KEY, enabled)
    }

    fun getPushNotificationsEnabledFlow(context: Context): Flow<Boolean> {
        return getPreferenceFlow(context, PUSH_NOTIFICATIONS_KEY, true)
    }

    suspend fun arePushNotificationsEnabled(context: Context): Boolean {
        return getPreference(context, PUSH_NOTIFICATIONS_KEY, true)
    }

    // Onboarding preferences
    suspend fun setOnboardingCompleted(context: Context, completed: Boolean) {
        savePreference(context, ONBOARDING_COMPLETED_KEY, completed)
    }

    fun getOnboardingCompletedFlow(context: Context): Flow<Boolean> {
        return getPreferenceFlow(context, ONBOARDING_COMPLETED_KEY, false)
    }

    suspend fun isOnboardingCompleted(context: Context): Boolean {
        return getPreference(context, ONBOARDING_COMPLETED_KEY, false)
    }

    suspend fun setFirstTimeUser(context: Context, isFirstTime: Boolean) {
        savePreference(context, FIRST_TIME_USER_KEY, isFirstTime)
    }

    fun getFirstTimeUserFlow(context: Context): Flow<Boolean> {
        return getPreferenceFlow(context, FIRST_TIME_USER_KEY, true)
    }

    suspend fun isFirstTimeUser(context: Context): Boolean {
        return getPreference(context, FIRST_TIME_USER_KEY, true)
    }

    // App version tracking
    suspend fun saveLastAppVersion(context: Context, version: String) {
        savePreference(context, LAST_APP_VERSION_KEY, version)
    }

    fun getLastAppVersionFlow(context: Context): Flow<String> {
        return getPreferenceFlow(context, LAST_APP_VERSION_KEY, "")
    }

    suspend fun getLastAppVersion(context: Context): String {
        return getPreference(context, LAST_APP_VERSION_KEY, "")
    }

    /**
     * Clear all app preferences (useful for logout or reset)
     */
    suspend fun clearAllAppPreferences(context: Context) {
        clearAllPreferences(context)
    }
}
