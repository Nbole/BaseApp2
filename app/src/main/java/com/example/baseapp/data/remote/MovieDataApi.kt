package com.example.baseapp.data.remote

import com.example.baseapp.BuildConfig
import com.example.baseapp.data.remote.di.KtorClient
import com.example.baseapp.data.remote.model.GenresResult
import com.example.baseapp.data.remote.model.MovieResult
import com.example.baseapp.data.remote.model.PreviewMovieResult
import com.example.baseapp.domain.MovieDataContract
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import java.io.IOException

class MovieDataApi : MovieDataContract {
    override suspend fun getLatestMovies(): KtorResponse<PreviewMovieResult> =
        try {
            KtorResponse.Success(
                KtorClient.httpClient.get {
                    url(BuildConfig.BASE_URL + "movie/now_playing")
                    parameter("api_key", BuildConfig.API_KEY)
                }
            )
        } catch (e: ClientRequestException) {
            KtorResponse.Error(data = null, message = e.message)
        } catch (e: IOException) {
            KtorResponse.Error(data = null, message = e.message.orEmpty())
        }

    override suspend fun getMovie(id: Int): KtorResponse<MovieResult> =
        try {
            KtorResponse.Success(
                KtorClient.httpClient.get {
                    url(BuildConfig.BASE_URL + "movie/$id")
                    parameter("api_key", BuildConfig.API_KEY)
                }
            )
        } catch (e: ClientRequestException) {
            KtorResponse.Error(data = null, message = e.message)
        } catch (e: IOException) {
            KtorResponse.Error(data = null, message = e.message.orEmpty())
        }

    override suspend fun getGenres(): KtorResponse<GenresResult> =
        try {
            KtorResponse.Success(
                KtorClient.httpClient.get {
                    url(BuildConfig.BASE_URL + "genre/movie/list")
                    parameter("api_key", BuildConfig.API_KEY)
                }
            )
        } catch (e: ClientRequestException) {
            KtorResponse.Error(data = null, message = e.message)
        } catch (e: IOException) {
            KtorResponse.Error(data = null, message = e.message.orEmpty())
        }
}
