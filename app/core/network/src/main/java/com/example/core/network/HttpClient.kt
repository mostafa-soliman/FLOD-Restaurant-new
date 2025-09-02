package com.example.core.network

import com.example.core.utils.filterNotNull
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(networkConfig: AppNetworkConfig): HttpClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = false
            encodeDefaults = true
            explicitNulls = false
            useAlternativeNames = false
        })
    }

    install(HttpTimeout) {
        requestTimeoutMillis = NetworkConstants.REQUEST_TIMEOUT_MILLIS   // Total request duration
        connectTimeoutMillis = NetworkConstants.CONNECT_TIMEOUT_MILLIS   // TCP handshake
        socketTimeoutMillis = NetworkConstants.SOCKET_TIMEOUT_MILLIS     // Data transfer timeout
    }

    defaultRequest {
        defaultHeaders(networkConfig).forEach { (key, value) ->
            header(key, value)
        }
    }

}

private fun defaultHeaders(networkConfig: AppNetworkConfig): Map<String, String> {
    return mapOf(
        NetworkConstants.KEY_APP_VERSION_HEADER to networkConfig.appVersionName,
        NetworkConstants.KEY_DEVICE_ID_HEADER to networkConfig.deviceId,
        NetworkConstants.KEY_PLATFORM_HEADER to NetworkConstants.PLATFORM_ANDROID
    ).filterNotNull()
}
