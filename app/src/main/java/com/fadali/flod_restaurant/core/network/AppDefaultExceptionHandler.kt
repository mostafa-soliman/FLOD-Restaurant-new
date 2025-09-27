package com.fadali.flod_restaurant.core.network

import com.smyvo.core.network.NetworkException
import com.smyvo.core.ui.network.DefaultExceptionHandler

class AppDefaultExceptionHandler : DefaultExceptionHandler {
    override fun handle(exception: Throwable) {
        when (exception) {
            is NetworkException.UnauthorizedException -> {
                // TODO: Handle refresh token
            }
            else -> {
                // TODO: Handle last uncaught exception
            }
        }
    }
}
