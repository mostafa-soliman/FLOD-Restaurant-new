package com.fadali.flod_restaurant.core.keystore.usecase.userid

import android.content.Context
import com.fadali.flod_restaurant.core.keystore.EncryptedPrefs.getEncryptedPrefs
import com.fadali.flod_restaurant.core.keystore.KeyStoreManager.decrypt
import com.fadali.flod_restaurant.core.keystore.KeyStoreManager.getOrCreateSecretKey
import com.fadali.flod_restaurant.core.keystore.usecase.userid.UserIdKeyStoreConstants.USER_ID_ENCRYPTION_KEY_ALIAS
import com.fadali.flod_restaurant.core.keystore.usecase.userid.UserIdKeyStoreConstants.PREF_KEY_USER_ID
import com.fadali.flod_restaurant.core.keystore.usecase.userid.UserIdKeyStoreConstants.PREF_KEY_USER_ID_IV
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
            val key = getOrCreateSecretKey(alias = USER_ID_ENCRYPTION_KEY_ALIAS)
            val decryptedUI = decrypt(
                Base64.getDecoder().decode(encryptedUserId),
                Base64.getDecoder().decode(ivUserId),
                key
            )
            return@withContext decryptedUI.toString(Charsets.UTF_8)
        }
        return@withContext null
    }
}
