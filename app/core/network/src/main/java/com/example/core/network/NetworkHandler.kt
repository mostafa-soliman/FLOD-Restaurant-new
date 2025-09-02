package com.example.core.network

import com.example.core.network.model.ErrorResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.delete
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.SerializationException

/**
 * Comprehensive network utility class for making HTTP requests with built-in response parsing.
 * 
 * @see [NetworkHandlerOverview.md](docs/NetworkHandlerOverview.md) for detailed documentation,
 * architecture overview, and comprehensive usage examples.
 */
class NetworkHandler(
    val httpClient: HttpClient
) {

    /**
     * Makes a GET HTTP request with comprehensive parameter support.
     * 
     * @param T The expected response data type
     * @param url The target URL for the request
     * @param pathParams Optional path segments to append to the URL
     * @param queryParams Optional query parameters
     * @param headers Optional HTTP headers
     * @param page Optional page number for pagination (1-indexed)
     * @param pageSize Optional page size for pagination
     * @return T containing the parsed response data
     * @throws NetworkException For network-related errors
     *
     * @see [GetMethodDocs.md](docs/GetMethodDocs.md) for detailed examples and usage patterns.
     */
    suspend inline fun <reified T> get(
        url: String,
        pathParams: List<String> = emptyList(),
        queryParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        page: Int? = null,
        pageSize: Int? = null
    ): T {
        return try {
            val finalUrl = if (pathParams.isNotEmpty()) buildUrl(url, pathParams) else url
            val finalParams = buildFinalQueryParams(queryParams, page, pageSize)
            
            val response = httpClient.get(finalUrl) {
                finalParams.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers.forEach { (key, value) ->
                    header(key, value)
                }
            }
            parseResponse<T>(response)
        } catch (e: Exception) {
            handleNetworkException(e)
        }
    }

    /**
     * Makes a POST HTTP request with JSON data or form data support.
     * 
     * @param T The expected response data type
     * @param url The target URL for the request
     * @param data The data to send in request body (JSON serialized unless formData provided)
     * @param pathParams Optional path segments to append to the URL
     * @param queryParams Optional query parameters
     * @param headers Optional HTTP headers
     * @param formData Optional form data (takes precedence over data parameter)
     * @return T containing the parsed response data
     * @throws NetworkException For network-related errors
     * 
     * @see [PostMethodDocs.md](docs/PostMethodDocs.md) for detailed examples and usage patterns.
     */
    suspend inline fun <reified T> post(
        url: String,
        data: Any? = null,
        pathParams: List<String> = emptyList(),
        queryParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        formData: Map<String, String>? = null
    ): T {
        return try {
            val finalUrl = if (pathParams.isNotEmpty()) buildUrl(url, pathParams) else url
            
            val response = httpClient.post(finalUrl) {
                when {
                    formData != null -> {
                        contentType(ContentType.Application.FormUrlEncoded)
                        setBody(FormDataContent(Parameters.build {
                            formData.forEach { (key, value) ->
                                append(key, value)
                            }
                        }))
                    }
                    data != null -> {
                        contentType(ContentType.Application.Json)
                        setBody(data)
                    }
                }
                queryParams.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers.forEach { (key, value) ->
                    header(key, value)
                }
            }
            parseResponse<T>(response)
        } catch (e: Exception) {
            handleNetworkException(e)
        }
    }

    /**
     * Makes a PUT HTTP request with JSON data for updating resources.
     * 
     * @param T The expected response data type
     * @param url The target URL for the request
     * @param data The data to send in request body (JSON serialized)
     * @param pathParams Optional path segments to append to the URL
     * @param queryParams Optional query parameters
     * @param headers Optional HTTP headers
     * @return T containing the parsed response data
     * @throws NetworkException For network-related errors
     * 
     * @see [PutMethodDocs.md](docs/PutMethodDocs.md) for detailed examples and usage patterns.
     */
    suspend inline fun <reified T> put(
        url: String,
        data: Any? = null,
        pathParams: List<String> = emptyList(),
        queryParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): T {
        return try {
            val finalUrl = if (pathParams.isNotEmpty()) buildUrl(url, pathParams) else url
            
            val response = httpClient.put(finalUrl) {
                if (data != null) {
                    contentType(ContentType.Application.Json)
                    setBody(data)
                }
                queryParams.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers.forEach { (key, value) ->
                    header(key, value)
                }
            }
            parseResponse<T>(response)
        } catch (e: Exception) {
            handleNetworkException(e)
        }
    }

    /**
     * Makes a DELETE HTTP request for removing resources.
     * 
     * @param T The expected response data type
     * @param url The target URL for the request
     * @param pathParams Optional path segments to append to the URL
     * @param queryParams Optional query parameters
     * @param headers Optional HTTP headers
     * @return T containing the parsed response data
     * @throws NetworkException For network-related errors
     * 
     * @see [DeleteMethodDocs.md](docs/DeleteMethodDocs.md) for detailed examples and usage patterns.
     */
    suspend inline fun <reified T> delete(
        url: String,
        pathParams: List<String> = emptyList(),
        queryParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): T {
        return try {
            val finalUrl = if (pathParams.isNotEmpty()) buildUrl(url, pathParams) else url
            
            val response = httpClient.delete(finalUrl) {
                queryParams.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers.forEach { (key, value) ->
                    header(key, value)
                }
            }
            parseResponse<T>(response)
        } catch (e: Exception) {
            handleNetworkException(e)
        }
    }

    /**
     * Builds a complete URL by appending path parameters to a base URL.
     * 
     * @param baseUrl The base URL (trailing slash will be removed if present)
     * @param pathParams List of path segments to append to the base URL
     * @return The complete URL with path parameters joined by forward slashes
     * 
     * @see [UtilityMethodsDocs.md](docs/UtilityMethodsDocs.md) for detailed examples.
     */
    fun buildUrl(baseUrl: String, pathParams: List<String>): String {
        return if (pathParams.isEmpty()) {
            baseUrl
        } else {
            val cleanBaseUrl = baseUrl.removeSuffix("/")
            val pathString = pathParams.joinToString("/")
            "$cleanBaseUrl/$pathString"
        }
    }

    /**
     * Creates an Authorization header with Bearer token format.
     * 
     * @param token The authentication token (without "Bearer " prefix)
     * @return A map containing the Authorization header with Bearer token
     * 
     * @see [UtilityMethodsDocs.md](docs/UtilityMethodsDocs.md) for detailed examples.
     */
    fun createAuthHeader(token: String): Map<String, String> {
        return mapOf(NetworkConstants.AUTHORIZATION_HEADER to NetworkConstants.BEARER_TOKEN_PREFIX + token)
    }



    /**
     * Handles network-related exceptions and maps them to appropriate NetworkException types.
     * 
     * @param e The caught exception
     * @return Nothing (always throws)
     * @throws NetworkException Appropriate NetworkException subtype based on the original exception
     * 
     * @see [UtilityMethodsDocs.md](docs/UtilityMethodsDocs.md) for detailed exception mapping information.
     */
    fun handleNetworkException(e: Exception): Nothing {
        when (e) {
            is NetworkException -> throw e
            is HttpRequestTimeoutException -> throw NetworkException.TimeoutException(e.message)

            is ConnectTimeoutException,
            is SocketTimeoutException -> throw NetworkException.NetworkConnectivityException(e.message)

            is JsonConvertException,
            is SerializationException -> throw NetworkException.SerializationException(e.message)

            else -> throw NetworkException.UnknownException(e.message)
        }
    }

    /**
     * Builds final query parameters by combining regular query params with pagination params.
     * 
     * @param queryParams User-provided query parameters as key-value pairs
     * @param page Optional page number for paginated requests (1-indexed)
     * @param pageSize Optional page size for paginated requests
     * @return Combined map of all query parameters ready for HTTP request
     * 
     * @see [UtilityMethodsDocs.md](docs/UtilityMethodsDocs.md) for detailed examples.
     */
    fun buildFinalQueryParams(
        queryParams: Map<String, String>,
        page: Int?,
        pageSize: Int?
    ): Map<String, String> {
        val paginationParams = mutableMapOf<String, String>()
        
        page?.let { paginationParams[NetworkConstants.KEY_PAGE_QUERY_PARAM] = it.toString() }
        pageSize?.let { paginationParams[NetworkConstants.KEY_PAGE_SIZE_QUERY_PARAM] = it.toString() }
        
        return queryParams + paginationParams
    }

    companion object {
        
        /**
         * Parses HTTP response and returns T directly with comprehensive error handling.
         * 
         * @param T The expected response data type for successful responses
         * @param response The HttpResponse from Ktor client to parse
         * @return T containing the parsed response data for success cases
         * @throws NetworkException Appropriate subtype based on HTTP status code or parsing errors
         * 
         * @see [ResponseParserDocs.md](docs/ResponseParserDocs.md) for detailed status code mapping and examples.
         */
        suspend inline fun <reified T> parseResponse(response: HttpResponse): T {
            try {
                when (response.status) {
                    HttpStatusCode.OK,
                    HttpStatusCode.Created,
                    HttpStatusCode.Accepted -> {
                        return response.body<T>()
                    }
                    HttpStatusCode.NoContent -> {
                        return Unit as T
                    }
                    HttpStatusCode.BadRequest -> {
                        val errorResponse = response.body<ErrorResponseModel>()
                        throw NetworkException.ClientException(
                            resultCode = errorResponse.resultCode,
                            errorMessage = errorResponse.errorMessage
                        )
                    }
                    HttpStatusCode.Unauthorized -> throw NetworkException.UnauthorizedException()
                    HttpStatusCode.Forbidden -> throw NetworkException.ForbiddenException()
                    HttpStatusCode.NotFound -> throw NetworkException.NotFoundException()
                    HttpStatusCode.InternalServerError -> throw NetworkException.ServerErrorException()
                    HttpStatusCode.RequestTimeout,
                    HttpStatusCode.GatewayTimeout -> throw NetworkException.TimeoutException()
                    else -> throw NetworkException.UnknownException(response.toString())
                }
            } catch (e: SerializationException) {
                throw NetworkException.SerializationException(e.message)
            } catch (e: NetworkException) {
                throw e
            } catch (e: Exception) {
                throw NetworkException.UnknownException(e.message)
            }
        }
    }

}
