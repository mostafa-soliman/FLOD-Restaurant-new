// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt.android) apply false
    // App Core
    alias(libs.plugins.core.network) apply false
    // Kotlin KSP
    alias(libs.plugins.kotlin.ksp) apply false
    // Kotlin parcelize
    alias(libs.plugins.kotlin.parcelize) apply false
}