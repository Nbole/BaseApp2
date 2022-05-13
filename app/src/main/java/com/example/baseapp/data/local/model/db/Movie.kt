package com.example.baseapp.data.local.model.db

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id")  val id: Int,
    @SerialName("original_title") val title: String?,
    val overview: String?,
    @SerialName("poster_path") val posterPath: String?,
)
