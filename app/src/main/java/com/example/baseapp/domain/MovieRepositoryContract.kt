package com.example.baseapp.domain

import com.example.baseapp.Resource
import com.example.baseapp.data.local.model.db.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryContract {
    fun getLatestMovies(): Flow<Resource<List<Movie>>>
}