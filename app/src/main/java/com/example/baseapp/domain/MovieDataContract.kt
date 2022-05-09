package com.example.baseapp.domain

import com.example.baseapp.data.remote.model.MovieResult
import retrofit2.Response

interface MovieDataContract {
    suspend fun getLatestMovies(): Response<MovieResult>
}
