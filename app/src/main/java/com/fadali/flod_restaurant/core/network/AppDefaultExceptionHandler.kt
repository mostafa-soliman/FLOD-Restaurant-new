package com.fadali.flod_restaurant.core.network

import com.smyvo.core.network.NetworkClientManager
import com.smyvo.core.network.NetworkException
import com.smyvo.core.ui.network.DefaultExceptionHandler

class AppDefaultExceptionHandler(
    private val networkClientManager: NetworkClientManager,
) : DefaultExceptionHandler {
    override fun handle(throwable: Throwable) {
        when (throwable) {
            is NetworkException.UnknownException -> {
                try {
                    networkClientManager.resetHttpClient()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        throwable.printStackTrace()
    }

    override suspend fun handleRefreshToken() { }

    override suspend fun onRefreshTokenExpired() { }

    override suspend fun onRefreshTokenFailed(exception: Exception) { }
}
