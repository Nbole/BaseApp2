package com.example.baseapp.domain

import kotlinx.coroutines.flow.Flow

interface MovieRepositoryContract {
    fun getLatestMovies(): Flow<DResponse<out List<MovieResponse>?>>
}