package com.fadali.flod_restaurant.core.keystore

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.core.content.edit

object EncryptedPrefs {
    const val KEY_SECURE_PREFS_NAME = "secure_prefs"

    fun getEncryptedPrefs(context: Context): SharedPreferences {
        // Create or get the “master key” used by EncryptedSharedPreferences
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            KEY_SECURE_PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun save(context: Context, key: String, value: String) {
        val prefs = getEncryptedPrefs(context)
        prefs.edit {
            putString(key, value)
        }
    }

    fun load(context: Context, key: String): String? {
        val prefs = getEncryptedPrefs(context)
        return prefs.getString(key, null)
    }

}
