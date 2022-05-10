package com.example.baseapp.data.local.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Movie(
    @SerialName("id") @PrimaryKey val id: Int,
    @SerialName("original_title") val title: String?,
    val overview: String?,
    @SerialName("poster_path") val posterPath: String?,
)
