package com.example.baseapp.data.local.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    @SerializedName("original_title") val title: String?,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
)
