package com.example.baseapp.data.local.model.db
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String?
)
