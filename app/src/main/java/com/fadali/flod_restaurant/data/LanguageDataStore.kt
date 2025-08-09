package com.fadali.flod_restaurant.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fadali.flod_restaurant.util.Constants.Companion.DATASTORE_NAME
import com.fadali.flod_restaurant.util.Constants.Companion.PREF_APP_LANGUAGE
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)
private val KEY_APP_LANGUAGE = stringPreferencesKey(PREF_APP_LANGUAGE)

object LanguageDataStore {

    suspend fun setLanguage(context: Context, tag: String) {
        context.dataStore.edit { it[KEY_APP_LANGUAGE] = tag }
    }

    suspend fun getLanguage(context: Context): String? =
        context.dataStore.data.map { it[KEY_APP_LANGUAGE] }.first()
}