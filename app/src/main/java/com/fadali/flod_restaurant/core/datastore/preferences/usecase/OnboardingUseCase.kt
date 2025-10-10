package com.fadali.flod_restaurant.core.datastore.preferences.usecase

import android.content.Context
import com.fadali.flod_restaurant.core.datastore.preferences.AppPreferences

/**
 * Use case for managing onboarding preferences
 */
class OnboardingUseCase(
    private val appPreferences: AppPreferences,
    private val context: Context,
) {
    /**
     * Mark onboarding as completed
     */
    suspend fun markOnboardingCompleted() {
        appPreferences.setOnboardingCompleted(context, true)
        appPreferences.setFirstTimeUser(context, false)
    }

    /**
     * Check if onboarding is completed
     */
    suspend fun isOnboardingCompleted(): Boolean {
        return appPreferences.isOnboardingCompleted(context)
    }

    /**
     * Check if user is first time user
     */
    suspend fun isFirstTimeUser(): Boolean {
        return appPreferences.isFirstTimeUser(context)
    }

    /**
     * Reset onboarding state (useful for testing or reset)
     */
    suspend fun resetOnboarding() {
        appPreferences.setOnboardingCompleted(context, false)
        appPreferences.setFirstTimeUser(context, true)
    }
}
