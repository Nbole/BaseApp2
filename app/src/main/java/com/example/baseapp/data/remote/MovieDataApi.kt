package com.example.baseapp.data.remote

import com.example.baseapp.data.remote.model.MovieResponse
import retrofit2.Response

class MovieDataApi(
    private val movieApiContract: MovieApiContract
) : MovieDataContract {
    override suspend fun getLatestMovies(): Response<MovieResponse> =
        movieApiContract.getLatestMovies("5e30e8afd06d2b8b9aae8eb164c85a29")
}
