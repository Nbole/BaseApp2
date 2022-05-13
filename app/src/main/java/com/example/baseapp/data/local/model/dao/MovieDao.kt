package com.example.baseapp.data.local.model.dao

import com.example.base.DispatchersProvider
import com.example.baseapp.MovieDataBase
import com.example.baseapp.data.local.model.db.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import movie.MovieEntity

interface MovieDao {
    suspend fun loadMovieById(id: Int): MovieEntity?
    fun loadAllMovies(): Flow<List<MovieEntity>>
    suspend fun deleteAllMovies()
    suspend fun saveMovies(input: List<Movie>)
}

class MovieDaoImpl(
    db: MovieDataBase,
    private val dispatchersProvider: DispatchersProvider,
) : MovieDao {
    private val queries = db.movieEntityQueries

    override suspend fun loadMovieById(id: Int): MovieEntity? {
        return withContext(dispatchersProvider.io) {
            queries.getMovieEntity(id.toLong()).executeAsOneOrNull()
        }
    }

    override fun loadAllMovies(): Flow<List<MovieEntity>> {
        return flow {
            emit(queries.getAllMovies().executeAsList())
        }
    }

    override suspend fun deleteAllMovies() {
        withContext(dispatchersProvider.io) {
            queries.deleteAllMovie()
        }
    }

    override suspend fun saveMovies(input: List<Movie>) {
        withContext(dispatchersProvider.io) {
            input.map {
                queries.saveMovies(
                    id = it.id.toLong(),
                    overview = it.overview,
                    posterPath = it.posterPath,
                    title = it.title
                )
            }
        }
    }
}
