package com.example.core.network

object NetworkConstants {
    const val REQUEST_TIMEOUT_MILLIS = 45_000L
    const val CONNECT_TIMEOUT_MILLIS = 15_000L
    const val SOCKET_TIMEOUT_MILLIS = 30_000L
    const val KEY_PAGE_QUERY_PARAM = "page"
    const val KEY_PAGE_SIZE_QUERY_PARAM = "pageSize"
    const val BEARER_TOKEN_PREFIX = "Bearer "

    /* App Common Headers */
    const val AUTHORIZATION_HEADER = "Authorization"
    const val KEY_APP_VERSION_HEADER = "X-App-Version"
    const val KEY_PLATFORM_HEADER = "X-Platform"
    const val PLATFORM_ANDROID = "Android"
    const val KEY_DEVICE_ID_HEADER = "X-Device-Id"
}
