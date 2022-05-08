package com.example.baseapp.data.repository

import com.example.baseapp.data.local.model.dao.MovieDao
import com.example.baseapp.data.remote.MovieDataContract
import com.example.baseapp.Resource
import com.example.baseapp.data.local.model.db.Movie
import com.example.baseapp.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val db: MovieDao,
    private val movieDataContract: MovieDataContract,
): MovieRepositoryContract {
    override fun getLatestMovies(): Flow<Resource<List<Movie>>> = networkBoundResource(
        { db.loadMovies() },
        { movieDataContract.getLatestMovies() },
        { response ->
            val movies = response.body()?.results
            if (!movies.isNullOrEmpty()) {
                db.saveMovies(movies)
            }
        }
    )
}
