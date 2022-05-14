package com.example.baseapp.data.local.model.dao

import com.example.base.di.DispatchersProvider
import com.example.baseapp.MovieDataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import movie.GenresEntities
import movie.MovieEntity
import movie.PreviewMovieEntity

interface MovieDao {
    suspend fun loadPreviewMovieById(id: Int): PreviewMovieEntity?
    fun loadAllPreviewMovies(): Flow<List<PreviewMovieEntity>>
    suspend fun deleteAllPreviewMovies()
    suspend fun savePreviewMovies(input: List<PreviewMovieEntity>)

    suspend fun loadMovieById(id: Int): MovieEntity?
    fun loadMovie(id: Int): Flow<MovieEntity?>
    suspend fun deleteAllMovies()
    suspend fun saveMovie(input: MovieEntity)

    fun loadGenres(): Flow<List<GenresEntities>>
    suspend fun saveGenres(list: List<GenresEntities>)
    suspend fun deleteGenres()
}

class MovieDaoImpl(
    db: MovieDataBase,
    private val dispatchersProvider: DispatchersProvider,
) : MovieDao {
    private val queries = db.movieEntityQueries

    override suspend fun loadPreviewMovieById(id: Int): PreviewMovieEntity? {
        return withContext(dispatchersProvider.io) {
            queries.getPreviewMovieEntity(id.toLong()).executeAsOneOrNull()
        }
    }

    override fun loadAllPreviewMovies(): Flow<List<PreviewMovieEntity>> {
        return flow { emit(queries.getAllPreviewMovies().executeAsList()) }
    }

    override suspend fun deleteAllPreviewMovies() {
        withContext(dispatchersProvider.io) { queries.deleteAllPreviewMovie() }
    }

    override suspend fun savePreviewMovies(input: List<PreviewMovieEntity>) {
        withContext(dispatchersProvider.io) {
            input.map {
                queries.savePreviewMovies(
                    id = it.id,
                    posterPath = it.posterPath,
                    title = it.title
                )
            }
        }
    }

    override suspend fun loadMovieById(id: Int): MovieEntity? {
        return withContext(dispatchersProvider.io) {
            queries.getMovieEntity(id.toLong()).executeAsOneOrNull()
        }
    }

    override fun loadMovie(id: Int): Flow<MovieEntity?> {
        return flow {
            emit(queries.getMovieEntity(id.toLong()).executeAsOneOrNull())
        }
    }

    override suspend fun deleteAllMovies() {
        withContext(dispatchersProvider.io) { queries.deleteAllMovie() }
    }

    override suspend fun saveMovie(input: MovieEntity) {
        withContext(dispatchersProvider.io) {
            queries.saveMovies(
                id = input.id,
                posterPath = input.posterPath,
                title = input.title,
                overview = input.overview,
                originCountry = input.originCountry,
            )
        }
    }

    override fun loadGenres(): Flow<List<GenresEntities>> {
        return flow {
            emit(queries.getGenresEntities().executeAsList())
        }
    }

    override suspend fun saveGenres(list: List<GenresEntities>) {
        withContext(dispatchersProvider.io) {
            list.map {
                queries.saveGenresEntities(
                    id = it.id,
                    name = it.name
                )
            }
        }
    }

    override suspend fun deleteGenres() {
        withContext(dispatchersProvider.io) { queries.deleteAllGenresEntities() }
    }
}
