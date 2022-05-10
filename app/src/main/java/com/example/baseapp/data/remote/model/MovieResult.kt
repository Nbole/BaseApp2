package com.example.baseapp.data.remote.model

import com.example.baseapp.data.local.model.db.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResult(
    @SerialName("results")
    val results: List<Movie>
)
