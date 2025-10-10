package com.fadali.flod_restaurant.core.datastore.preferences.usecase

import android.content.Context
import com.fadali.flod_restaurant.core.datastore.preferences.AppPreferences

class UserPrefsUseCase(
    private val appPreferences: AppPreferences,
    private val context: Context,
) {
    /**
     * Check if the User is logged in
     */
    suspend fun isLoggedIn(): Boolean {
        return appPreferences.isLoggedIn(context)
    }

    /**
     * Mark the the User as logged in
     */
    suspend fun markUserAsLoggedIn() {
        appPreferences.setLoggedIn(context, true)
    }
}
