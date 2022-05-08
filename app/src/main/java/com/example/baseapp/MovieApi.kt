package com.example.baseapp

import com.example.baseapp.data.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("now_playing")
    suspend fun getLatestMovies(@Query("api_key") apiKey: String, ): Response<MovieResponse>
}
