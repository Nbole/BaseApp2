package com.example.baseapp.domain

import com.example.baseapp.data.remote.model.GenresResult
import com.example.baseapp.data.remote.model.MovieResult
import com.example.baseapp.data.remote.model.PreviewMovieResult
import com.example.networkbound.SerialResponse

interface MovieDataContract {
    suspend fun getLatestMovies(): SerialResponse<PreviewMovieResult>
    suspend fun getMovie(id: Int): SerialResponse<MovieResult>
    suspend fun getGenres(): SerialResponse<GenresResult>
}
