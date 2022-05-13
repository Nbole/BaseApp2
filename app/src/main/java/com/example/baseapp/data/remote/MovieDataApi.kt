package com.example.baseapp.data.remote

import com.example.baseapp.BuildConfig
import com.example.baseapp.data.remote.di.KtorClient
import com.example.baseapp.data.remote.model.MovieResult
import com.example.baseapp.domain.MovieDataContract
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class MovieDataApi : MovieDataContract {
    override suspend fun getLatestMovies(): Response<MovieResult> =
        KtorClient.httpClient.get<MovieResult> {
            url(BuildConfig.BASE_URL + "now_playing")
            parameter("api_key", BuildConfig.API_KEY)
        }.toResponse()
}
