package com.example.baseapp.data

sealed class WResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : WResponse<T>(data)
    class Loading<T>(data: T? = null) : WResponse<T>(data)
    class Error<T>(message: String, data: T? = null) : WResponse<T>(data, message)
}
