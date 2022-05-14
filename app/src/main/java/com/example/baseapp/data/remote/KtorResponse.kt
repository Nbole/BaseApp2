package com.example.baseapp.data.remote

sealed class KtorResponse<T> {
    data class Success<T>(val data: T) : KtorResponse<T>()
    data class Error<T>(val message: String, val data: T? = null) : KtorResponse<T>()
}