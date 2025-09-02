package com.example.core.network

/**
 * Configuration class specific to each project.
 * Must be provided in your project global DI module
 */
data class AppNetworkConfig(
    val appVersionName: String?,
    val deviceId: String?
)
