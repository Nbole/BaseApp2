package com.example.baseapp.data.local.model.db

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    @SerialName("original_title") val title: String?,
    @SerialName("poster_path") val posterPath: String?,
    val overview: String?,
    @SerialName("origin_country") val originCountry: String?,
)
