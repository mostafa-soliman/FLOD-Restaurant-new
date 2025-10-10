package com.fadali.flod_restaurant.feature.auth.domain.usecase

import android.content.Context
import com.fadali.flod_restaurant.core.keystore.EncryptedPrefs.getEncryptedPrefs
import com.fadali.flod_restaurant.core.keystore.KeyStoreManager.decrypt
import com.fadali.flod_restaurant.core.keystore.KeyStoreManager.getOrCreateSecretKey
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Base64

class GetUserIdUseCase(
    private val context: Context,
    private val dispatchers: AppCoroutineDispatcher
) {
    suspend operator fun invoke(): String? = withContext(dispatchers.io) {
        val prefs = getEncryptedPrefs(context)
        val encryptedUserId = prefs.getString(PREF_KEY_USER_ID, null)
        val ivUserId = prefs.getString(PREF_KEY_USER_ID_IV, null)
        if (encryptedUserId != null && ivUserId != null) {
            val key = getOrCreateSecretKey(alias = ENCRYPTION_KEY_ALIAS)
            val decryptedUI = decrypt(
                Base64.getDecoder().decode(encryptedUserId),
                Base64.getDecoder().decode(ivUserId),
                key
            )
            return@withContext decryptedUI.toString(Charsets.UTF_8)
        }
        return@withContext null
    }

    private companion object {
        const val PREF_KEY_USER_ID = "user_id"
        const val PREF_KEY_USER_ID_IV = "user_id_iv"
        const val ENCRYPTION_KEY_ALIAS = "flod_user_id_key"
    }
}
