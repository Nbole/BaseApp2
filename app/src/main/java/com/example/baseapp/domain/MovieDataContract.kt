package com.example.baseapp.domain

import com.example.baseapp.data.remote.KtorResponse
import com.example.baseapp.data.remote.model.GenresResult
import com.example.baseapp.data.remote.model.MovieResult
import com.example.baseapp.data.remote.model.PreviewMovieResult

interface MovieDataContract {
    suspend fun getLatestMovies(): KtorResponse<PreviewMovieResult>
    suspend fun getMovie(id: Int): KtorResponse<MovieResult>
    suspend fun getGenres(): KtorResponse<GenresResult>
}
