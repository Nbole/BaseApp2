package com.example.baseapp

import com.example.baseapp.data.remote.model.MovieResponse
import retrofit2.Response

interface MovieDataContract {
    suspend fun getLatestMovies(): Response<MovieResponse>
}