package com.example.core.network

import com.example.core.utils.exception.BaseException

sealed class NetworkException(errorMessage: String?) : BaseException(errorMessage) {
    data class ClientException(
        val resultCode: String? = null,
        val errorMessage: String? = null
    ) : NetworkException(errorMessage)
    data class UnauthorizedException(val errorMessage: String? = null) : NetworkException(errorMessage)
    data class ForbiddenException(val errorMessage: String? = null) : NetworkException(errorMessage)
    data class NotFoundException(val errorMessage: String? = null) : NetworkException(errorMessage)
    data class ServerErrorException(val errorMessage: String? = null) : NetworkException(errorMessage)
    data class TimeoutException(val errorMessage: String? = null) : NetworkException(errorMessage)
    data class NetworkConnectivityException(val errorMessage: String? = null) : NetworkException(errorMessage)
    data class SerializationException(val errorMessage: String? = null) : NetworkException(errorMessage)
    data class UnknownException(val errorMessage: String? = null) : NetworkException(errorMessage)
}
