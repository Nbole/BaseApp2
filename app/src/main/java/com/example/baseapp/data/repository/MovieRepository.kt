package com.example.baseapp.data.repository

import com.example.base.mappers.BaseMapper
import com.example.baseapp.data.networkBoundResource
import com.example.baseapp.data.local.model.dao.MovieDao
import com.example.baseapp.data.local.model.db.Movie
import com.example.baseapp.data.remote.Response
import com.example.baseapp.data.remote.mapResponse
import com.example.baseapp.data.remote.model.MovieResult
import com.example.baseapp.domain.model.DResponse
import com.example.baseapp.domain.MovieDataContract
import com.example.baseapp.domain.MovieRepositoryContract
import com.example.baseapp.domain.model.vo.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import movie.MovieEntity

class MovieRepository(
    private val db: MovieDao,
    private val movieDataContract: MovieDataContract,
    private val movieMapper: BaseMapper<MovieEntity, MovieResponse>
) : MovieRepositoryContract {
    override fun getLatestMovies(): Flow<DResponse<List<MovieResponse>>> = networkBoundResource(
        { db.loadAllMovies() },
        { movieDataContract.getLatestMovies() },
        { response ->
            val movies = (response as Response.Success).data.results
            db.deleteAllMovies()
            if (movies.isNotEmpty()) {
                db.saveMovies(movies)
            }
        }
    ).map { w ->
        w.mapResponse {
            movieMapper.transform(it)
        }
    }
}
