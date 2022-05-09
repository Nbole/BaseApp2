package com.example.baseapp.domain.model

sealed class DResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : DResponse<T>(data)
    class Loading<T>(data: T? = null) : DResponse<T>(data)
    class Error<T>(message: String, data: T? = null) : DResponse<T>(data, message)
}