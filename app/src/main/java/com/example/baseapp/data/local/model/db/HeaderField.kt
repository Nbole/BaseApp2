package com.example.baseapp.data.local.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TotalHeaders(
    @PrimaryKey val id: String,
    val total: Int?,
)

@Entity
data class HeaderField(
    @PrimaryKey val id: String,
    val webPublicationDate: String,
    val webTitle: String,
    val thumbnail: String,
)

@Entity
data class BodyField(
    @PrimaryKey val bodyId: String,
    val headLine: String,
    val body: String,
)

data class NewsDetail(
    val webPublicationDate: String,
    val webTitle: String,
    val thumbnail: String,
    val headLine: String,
    val body: String,
)

@Entity(primaryKeys = ["headerId", "page", "query"])
data class HeaderTable(
    val query: String,
    val headerId: String,
    val index: Int,
    val page: Int
)
