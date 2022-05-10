package com.example.baseapp.domain

import com.example.baseapp.data.remote.Response
import com.example.baseapp.data.remote.model.MovieResult

interface MovieDataContract {
    suspend fun getLatestMovies(): Response<MovieResult>
}
