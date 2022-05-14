package com.example.baseapp.domain.model

sealed class DResponse<T> {
    data class Success<T>(val data: T) : DResponse<T>()
    data class Loading<T>(val data: T? = null) : DResponse<T>()
    data class Error<T>(val message: String, val data: T? = null) : DResponse<T>()
}