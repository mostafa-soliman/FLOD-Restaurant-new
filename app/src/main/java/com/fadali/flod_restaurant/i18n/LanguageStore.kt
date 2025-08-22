package com.fadali.flod_restaurant.i18n

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.langDataStore by preferencesDataStore(name = "lang_store")

object LanguageStore {
    private val KEY_LANG = stringPreferencesKey("lang")

    suspend fun save(context: Context, lang: String) {
        context.langDataStore.edit { it[KEY_LANG] = lang }
    }

    suspend fun load(context: Context): String? {
        return context.langDataStore.data.map { it[KEY_LANG] }.first()
    }
}
