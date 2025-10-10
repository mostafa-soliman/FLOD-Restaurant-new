package com.fadali.flod_restaurant.feature.auth.domain.usecase

import android.content.Context
import androidx.core.content.edit
import com.fadali.flod_restaurant.core.keystore.EncryptedPrefs.getEncryptedPrefs
import com.fadali.flod_restaurant.core.keystore.KeyStoreManager.encrypt
import com.fadali.flod_restaurant.core.keystore.KeyStoreManager.getOrCreateSecretKey
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Base64

class SaveUserIdUseCase(
    private val context: Context,
    private val dispatchers: AppCoroutineDispatcher
) {
    suspend operator fun invoke(userId: String) = withContext(dispatchers.io) {
        val key = getOrCreateSecretKey(alias = ENCRYPTION_KEY_ALIAS)
        val (encryptedUI, ivUI) = encrypt(userId.toByteArray(Charsets.UTF_8), key)
        val prefs = getEncryptedPrefs(context)
        prefs.edit {
            putString(PREF_KEY_USER_ID, Base64.getEncoder().encodeToString(encryptedUI))
            putString(PREF_KEY_USER_ID_IV, Base64.getEncoder().encodeToString(ivUI))
        }
    }

    private companion object {
        const val PREF_KEY_USER_ID = "user_id"
        const val PREF_KEY_USER_ID_IV = "user_id_iv"
        const val ENCRYPTION_KEY_ALIAS = "flod_user_id_key"
    }
}
