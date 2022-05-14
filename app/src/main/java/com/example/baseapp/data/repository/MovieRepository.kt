package com.example.baseapp.data.repository

import com.example.baseapp.data.local.model.dao.MovieDao
import com.example.baseapp.data.local.model.db.Genre
import com.example.baseapp.data.local.model.db.Movie
import com.example.baseapp.data.networkBoundResource
import com.example.baseapp.data.remote.KtorResponse
import com.example.baseapp.data.remote.mapResponse
import com.example.baseapp.domain.MovieDataContract
import com.example.baseapp.domain.MovieRepositoryContract
import com.example.baseapp.domain.model.DomainResponse
import com.example.baseapp.domain.model.vo.GenreResponse
import com.example.baseapp.domain.model.vo.MovieResponse
import com.example.baseapp.domain.model.vo.PreviewMovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import movie.GenresEntities
import movie.MovieEntity
import movie.PreviewMovieEntity

class MovieRepository(
    private val db: MovieDao,
    private val movieDataContract: MovieDataContract
) : MovieRepositoryContract {
    override fun getLatestMovies(): Flow<DomainResponse<List<PreviewMovieResponse>>> =
        networkBoundResource(
            { db.loadAllPreviewMovies() },
            { movieDataContract.getLatestMovies() },
            { response ->
                val movies = (response as KtorResponse.Success).data.results
                db.deleteAllPreviewMovies()
                if (movies.isNotEmpty()) {
                    db.savePreviewMovies(
                        movies.map {
                            PreviewMovieEntity(
                                it.id.toLong(),
                                it.title,
                                it.posterPath
                            )
                        }
                    )
                }
            }
        ).map { w ->
            w.mapResponse { p ->
                p.map {
                    PreviewMovieResponse(
                        id = it.id,
                        title = it.title,
                        posterPath = it.posterPath
                    )
                }
            }
        }

    override fun getMovie(id: Int): Flow<DomainResponse<MovieResponse?>> = networkBoundResource(
        { db.loadMovie(id) },
        { movieDataContract.getMovie(id) },
        { response ->
            val movies: Movie = (response as KtorResponse.Success).data.results
            db.deleteAllMovies()
            db.saveMovie(
                MovieEntity(
                    movies.id.toLong(),
                    movies.title,
                    movies.overview,
                    movies.originCountry,
                    movies.posterPath
                )
            )
        }
    ).map { w ->
        w.mapResponse {
            it?.let {
                MovieResponse(
                    id = it.id,
                    title = it.title,
                    posterPath = it.posterPath,
                    overview = it.overview,
                    originCountry = it.originCountry
                )
            }
        }
    }

    override fun getGenres(): Flow<DomainResponse<List<GenreResponse>>> = networkBoundResource(
        { db.loadGenres() },
        { movieDataContract.getGenres() },
        { response ->
            val genres: List<Genre> = (response as KtorResponse.Success).data.genres
            db.deleteAllMovies()
            db.saveGenres(
                genres.map {
                    GenresEntities(
                        it.id.toLong(),
                        it.name
                    )
                }
            )
        }
    ).map { w ->
        w.mapResponse { p ->
            p.map {
                GenreResponse(
                   it.id,
                   it.name
                )
            }
        }
    }
}
