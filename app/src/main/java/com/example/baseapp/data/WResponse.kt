package com.example.baseapp.data

sealed class WResponse<T> {
    data class Success<T>(val data: T) : WResponse<T>()
    data class Loading<T>(val data: T? = null) : WResponse<T>()
    data class Error<T>(val message: String, val data: T? = null) : WResponse<T>()
}
