package com.example.baseapp.domain.model.vo

data class MovieResponse(
    val id: Long,
    val title: String?,
    val posterPath: String?,
    val overview: String?,
    val originCountry: String?,
)
