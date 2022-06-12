package com.example.baseapp.data.remote.model

import com.example.baseapp.data.local.model.db.PreviewMovie
import kotlinx.serialization.Serializable

@Serializable
data class PreviewMovieResult(val results: List<PreviewMovie>)
