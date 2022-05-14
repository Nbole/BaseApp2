package com.example.baseapp.data.remote.model
import com.example.baseapp.data.local.model.db.Genre
import kotlinx.serialization.Serializable

@Serializable
data class GenresResult(val genres: List<Genre>)
