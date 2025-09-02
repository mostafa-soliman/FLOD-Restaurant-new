package com.example.core.network

import io.ktor.client.HttpClient
import org.koin.dsl.module

/**
 * All Network related dependencies.
 * Must be provided to your project global DI module
 */
val networkModule = module {
    single<HttpClient> {
        createHttpClient(get())
    }

    single<NetworkHandler> {
        NetworkHandler(get())
    }
}
