package com.fadali.flod_restaurant.core.domain.usecase

import android.content.Context
import com.fadali.flod_restaurant.core.datastore.preferences.AppPreferences

/**
 * Use case for managing onboarding preferences
 */
class OnboardingUseCase(
    private val appPreferences: AppPreferences
) {
    /**
     * Mark onboarding as completed
     */
    suspend fun markOnboardingCompleted(context: Context) {
        appPreferences.setOnboardingCompleted(context, true)
        appPreferences.setFirstTimeUser(context, false)
    }

    /**
     * Check if onboarding is completed
     */
    suspend fun isOnboardingCompleted(context: Context): Boolean {
        return appPreferences.isOnboardingCompleted(context)
    }

    /**
     * Check if user is first time user
     */
    suspend fun isFirstTimeUser(context: Context): Boolean {
        return appPreferences.isFirstTimeUser(context)
    }

    /**
     * Reset onboarding state (useful for testing or reset)
     */
    suspend fun resetOnboarding(context: Context) {
        appPreferences.setOnboardingCompleted(context, false)
        appPreferences.setFirstTimeUser(context, true)
    }
}
