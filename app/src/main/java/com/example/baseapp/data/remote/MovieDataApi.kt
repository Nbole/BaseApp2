package com.example.baseapp.data.remote

import com.example.baseapp.BuildConfig
import com.example.baseapp.data.remote.di.KtorClient
import com.example.baseapp.data.remote.model.MovieResult
import com.example.baseapp.domain.MovieDataContract
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import java.io.IOException

class MovieDataApi : MovieDataContract {
    override suspend fun getLatestMovies(): Response<MovieResult> =
        KtorClient.httpClient.get<MovieResult> {
            url(BuildConfig.BASE_URL + "now_playing")
            parameter("api_key", BuildConfig.API_KEY)
        }.toResponse()
}

fun <T> T.toResponse(): Response<T> {
    return try {
        Response.Success(this)
    } catch (e: ClientRequestException) {
        Response.Error(data = null, message = e.message)
    } catch (e: IOException) {
        Response.Error(data = null, message = e.message.orEmpty())
    }
}
