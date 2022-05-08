package com.example.baseapp

import com.example.baseapp.data.remote.model.MovieResponse
import retrofit2.Response

class MovieDataApi(
    private val movieApi: MovieApi
) : MovieDataContract {
    override suspend fun getLatestMovies(): Response<MovieResponse> =
        movieApi.getLatestMovies("5e30e8afd06d2b8b9aae8eb164c85a29")
}
