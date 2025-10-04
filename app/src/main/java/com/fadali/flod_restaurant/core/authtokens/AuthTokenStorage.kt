package com.fadali.flod_restaurant.core.authtokens

import android.content.Context
import androidx.core.content.edit
import com.fadali.flod_restaurant.core.keystore.EncryptedPrefs.getEncryptedPrefs
import com.fadali.flod_restaurant.core.keystore.KeyStoreManager.decrypt
import com.fadali.flod_restaurant.core.keystore.KeyStoreManager.encrypt
import com.fadali.flod_restaurant.core.keystore.KeyStoreManager.getOrCreateSecretKey
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Base64

class AuthTokenStorage(private val dispatchers: AppCoroutineDispatcher) {

    suspend fun saveTokens(
        context: Context,
        accessToken: String,
        refreshToken: String
    ) = withContext(dispatchers.io) {
        val key = getOrCreateSecretKey(alias = ENCRYPTION_KEY_ALIAS)
        val (encryptedAT, ivAT) = encrypt(accessToken.toByteArray(Charsets.UTF_8), key)
        val (encryptedRT, ivRT) = encrypt(refreshToken.toByteArray(Charsets.UTF_8), key)
        val prefs = getEncryptedPrefs(context)
        prefs.edit {
            putString(PREF_KEY_ACCESS_TOKEN, Base64.getEncoder().encodeToString(encryptedAT))
            putString(PREF_KEY_ACCESS_IV, Base64.getEncoder().encodeToString(ivAT))
            putString(PREF_KEY_REFRESH_TOKEN, Base64.getEncoder().encodeToString(encryptedRT))
            putString(PREF_KEY_REFRESH_IV, Base64.getEncoder().encodeToString(ivRT))
        }
    }

    suspend fun loadTokens(
        context: Context
    ): Pair<String?, String?> = withContext(dispatchers.io) {
        val prefs = getEncryptedPrefs(context)
        val encryptedAT = prefs.getString(PREF_KEY_ACCESS_TOKEN, null)
        val ivAT = prefs.getString(PREF_KEY_ACCESS_IV, null)
        val encryptedRT = prefs.getString(PREF_KEY_REFRESH_TOKEN, null)
        val ivRT = prefs.getString(PREF_KEY_REFRESH_IV, null)
        if (encryptedAT != null && ivAT != null && encryptedRT != null && ivRT != null) {
            val key = getOrCreateSecretKey(alias = ENCRYPTION_KEY_ALIAS)
            val accessToken = decrypt(
                Base64.getDecoder().decode(encryptedAT),
                Base64.getDecoder().decode(ivAT),
                key
            ).toString(Charsets.UTF_8)
            val refreshToken = decrypt(
                Base64.getDecoder().decode(encryptedRT),
                Base64.getDecoder().decode(ivRT),
                key
            ).toString(Charsets.UTF_8)
            return@withContext Pair(accessToken, refreshToken)
        }
        return@withContext Pair(null, null)
    }

    private companion object {
        const val PREF_KEY_ACCESS_TOKEN = "access_token"
        const val PREF_KEY_ACCESS_IV = "access_iv"
        const val PREF_KEY_REFRESH_TOKEN = "refresh_token"
        const val PREF_KEY_REFRESH_IV = "refresh_iv"
        const val ENCRYPTION_KEY_ALIAS = "flod_auth_tokens_key"
    }
}
