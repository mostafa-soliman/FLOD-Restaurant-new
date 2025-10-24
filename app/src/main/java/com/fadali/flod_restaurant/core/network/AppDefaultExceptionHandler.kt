package com.fadali.flod_restaurant.core.network

import com.smyvo.core.ui.network.DefaultExceptionHandler

class AppDefaultExceptionHandler(
) : DefaultExceptionHandler {
    override fun handle(throwable: Throwable) {
        throwable.printStackTrace()
    }

    override suspend fun handleRefreshToken() { }

    override suspend fun onRefreshTokenExpired() { }

    override suspend fun onRefreshTokenFailed(exception: Exception) { }
}
