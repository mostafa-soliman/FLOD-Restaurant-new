package com.fadali.flod_restaurant.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Base DataStore for app-wide preferences
 */
private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

abstract class AppDataStore(
    private val dispatchers: AppCoroutineDispatcher
) {
    /**
     * Get the app DataStore instance
     */
    protected fun getDataStore(context: Context): DataStore<Preferences> = context.appDataStore

    /**
     * Save a preference value
     */
    protected suspend fun <T> savePreference(
        context: Context,
        key: Preferences.Key<T>,
        value: T
    ) = withContext(dispatchers.io) {
        getDataStore(context).edit { preferences ->
            preferences[key] = value
        }
    }

    /**
     * Get a preference value as Flow
     */
    protected fun <T> getPreferenceFlow(
        context: Context,
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> = getDataStore(context).data.map { preferences ->
        preferences[key] ?: defaultValue
    }

    /**
     * Get a preference value synchronously (suspending)
     */
    protected suspend fun <T> getPreference(
        context: Context,
        key: Preferences.Key<T>,
        defaultValue: T
    ): T = withContext(dispatchers.io) {
        getPreferenceFlow(context, key, defaultValue).first()
    }

    /**
     * Remove a preference
     */
    protected suspend fun <T> removePreference(
        context: Context,
        key: Preferences.Key<T>
    ) = withContext(dispatchers.io) {
        getDataStore(context).edit { preferences ->
            preferences.remove(key)
        }
    }

    /**
     * Clear all preferences
     */
    protected suspend fun clearAllPreferences(
        context: Context
    ) = withContext(dispatchers.io) {
        getDataStore(context).edit { preferences ->
            preferences.clear()
        }
    }
}
