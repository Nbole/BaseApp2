package com.example.baseapp.domain

import com.example.baseapp.domain.model.DResponse
import com.example.baseapp.domain.model.vo.MovieResponse
import kotlinx.coroutines.flow.Flow
import movie.MovieEntity

interface MovieRepositoryContract {
    fun getLatestMovies(): Flow<DResponse<List<MovieResponse>>>
}