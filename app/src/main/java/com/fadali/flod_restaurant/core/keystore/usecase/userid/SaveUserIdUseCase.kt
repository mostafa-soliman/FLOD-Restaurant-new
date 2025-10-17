package com.fadali.flod_restaurant.core.keystore.usecase.userid

import android.content.Context
import androidx.core.content.edit
import com.fadali.flod_restaurant.core.keystore.EncryptedPrefs.getEncryptedPrefs
import com.fadali.flod_restaurant.core.keystore.KeyStoreManager.encrypt
import com.fadali.flod_restaurant.core.keystore.KeyStoreManager.getOrCreateSecretKey
import com.fadali.flod_restaurant.core.keystore.usecase.userid.UserIdKeyStoreConstants.USER_ID_ENCRYPTION_KEY_ALIAS
import com.fadali.flod_restaurant.core.keystore.usecase.userid.UserIdKeyStoreConstants.PREF_KEY_USER_ID
import com.fadali.flod_restaurant.core.keystore.usecase.userid.UserIdKeyStoreConstants.PREF_KEY_USER_ID_IV
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Base64

class SaveUserIdUseCase(
    private val context: Context,
    private val dispatchers: AppCoroutineDispatcher
) {
    suspend operator fun invoke(userId: String) = withContext(dispatchers.io) {
        val key = getOrCreateSecretKey(alias = USER_ID_ENCRYPTION_KEY_ALIAS)
        val (encryptedUI, ivUI) = encrypt(userId.toByteArray(Charsets.UTF_8), key)
        val prefs = getEncryptedPrefs(context)
        prefs.edit {
            putString(PREF_KEY_USER_ID, Base64.getEncoder().encodeToString(encryptedUI))
            putString(PREF_KEY_USER_ID_IV, Base64.getEncoder().encodeToString(ivUI))
        }
    }
}
