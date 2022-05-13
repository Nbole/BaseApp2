package com.example.baseapp.data.remote

import io.ktor.client.features.ClientRequestException
import java.io.IOException

sealed class Response<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Response<T>(data)
    class Loading<T>(data: T? = null) : Response<T>(data)
    class Error<T>(message: String, data: T? = null) : Response<T>(data, message)
}

fun <T> T.toResponse(): Response<T> {
    return try {
        Response.Success(this)
    } catch (e: ClientRequestException) {
        Response.Error(data = null, message = e.message)
    } catch (e: IOException) {
        Response.Error(data = null, message = e.message.orEmpty())
    }
}