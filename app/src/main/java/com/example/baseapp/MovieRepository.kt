package com.example.baseapp

import com.example.baseapp.db.PagedMovies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val db: MovieDao,
    private val movieDataSource: MovieDataSource,
) {
    fun getLatestMovies(): Flow<Resource<List<PagedMovies>>> = networkBoundResource(
        { db.loadMovies() },
        { movieDataSource.getLatestMovies() },
        { response ->
            val movies = response.body()?.results
            if (!movies.isNullOrEmpty()) {
                db.saveMovies(movies)
            }
        }
    )
}
