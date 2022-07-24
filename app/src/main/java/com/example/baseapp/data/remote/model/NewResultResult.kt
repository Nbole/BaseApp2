package com.example.baseapp.data.remote.model

import kotlinx.serialization.Serializable
@Serializable
data class NewResult(
    val response: NewsHeader,
)

@Serializable
data class NewsHeader(
    val total: Int? = null,
    val results: List<NewsDetail>? = null
)

@Serializable
data class NewsDetail(
    val id: String,
    val webPublicationDate: String? = null,
    val webTitle: String? = null,
    val fields: NewsDetailFields? = null,
)

@Serializable
data class NewsDetailFields(
    val headline: String? = null,
    val body: String? = null,
    val starRating: Int? = null,
    val thumbnail: String? = null,
)
