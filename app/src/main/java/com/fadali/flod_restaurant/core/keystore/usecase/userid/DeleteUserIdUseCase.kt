package com.fadali.flod_restaurant.core.keystore.usecase.userid

import android.content.Context
import androidx.core.content.edit
import com.fadali.flod_restaurant.core.keystore.EncryptedPrefs.getEncryptedPrefs
import com.fadali.flod_restaurant.core.keystore.usecase.userid.UserIdKeyStoreConstants.PREF_KEY_USER_ID
import com.fadali.flod_restaurant.core.keystore.usecase.userid.UserIdKeyStoreConstants.PREF_KEY_USER_ID_IV
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteUserIdUseCase(
    private val context: Context,
    private val dispatchers: AppCoroutineDispatcher
) {
    suspend operator fun invoke() = withContext(dispatchers.io) {
        val prefs = getEncryptedPrefs(context)
        prefs.edit {
            remove(PREF_KEY_USER_ID)
            remove(PREF_KEY_USER_ID_IV)
        }
    }
}
