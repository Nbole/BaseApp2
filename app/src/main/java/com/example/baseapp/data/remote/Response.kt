package com.example.baseapp.data.remote

import io.ktor.client.features.ClientRequestException
import java.io.IOException

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Loading<T>(val data: T? = null) : Response<T>()
    data class Error<T>(val message: String, val data: T? = null) : Response<T>()
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