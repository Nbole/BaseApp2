package com.example.baseapp.data.remote

import com.example.baseapp.BuildConfig
import com.example.baseapp.data.remote.model.GenresResult
import com.example.baseapp.data.remote.model.MovieResult
import com.example.baseapp.data.remote.model.NewResult
import com.example.baseapp.data.remote.model.PreviewMovieResult
import com.example.baseapp.domain.MovieDataContract
import com.example.baseapp.domain.NewsDataContract
import com.example.networkbound.KtorClient
import com.example.networkbound.SerialResponse
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import java.io.IOException

class NewsDataApi: NewsDataContract {
    override suspend fun search(q: String, page: Int, size: Int): SerialResponse<NewResult> =
        try {
            SerialResponse.Success(
                KtorClient.httpClient.get {
                    url(BuildConfig.BASE_NEWS_URL)
                    parameter("q",q)
                    parameter("page",page)
                    parameter("page-size", size)
                    parameter("show-fields","starRating,body,headline,thumbnail,total")
                    parameter("format","json")
                    parameter("from-date","20")
                    parameter("api-key", BuildConfig.API_KEY)
                }
            )
        } catch (e: ClientRequestException) {
            SerialResponse.Error(data = null, message = e.message)
        } catch (e: IOException) {
            SerialResponse.Error(data = null, message = e.message.orEmpty())
        }
}

class MovieDataApi : MovieDataContract {
    override suspend fun getLatestMovies(): SerialResponse<PreviewMovieResult> =
        try {
            SerialResponse.Success(
                KtorClient.httpClient.get {
                    url(BuildConfig.BASE_NEWS_URL + "movie/now_playing")
                    parameter("api_key", BuildConfig.API_KEY)
                }
            )
        } catch (e: ClientRequestException) {
            SerialResponse.Error(data = null, message = e.message)
        } catch (e: IOException) {
            SerialResponse.Error(data = null, message = e.message.orEmpty())
        }

    override suspend fun getMovie(id: Int): SerialResponse<MovieResult> =
        try {
            SerialResponse.Success(
                KtorClient.httpClient.get {
                    url(BuildConfig.BASE_NEWS_URL + "movie/$id")
                    parameter("api_key", BuildConfig.API_KEY)
                }
            )
        } catch (e: ClientRequestException) {
            SerialResponse.Error(data = null, message = e.message)
        } catch (e: IOException) {
            SerialResponse.Error(data = null, message = e.message.orEmpty())
        }

    override suspend fun getGenres(): SerialResponse<GenresResult> =
        try {
            SerialResponse.Success(
                KtorClient.httpClient.get {
                    url(BuildConfig.BASE_NEWS_URL + "genre/movie/list")
                    parameter("api_key", BuildConfig.API_KEY)
                }
            )
        } catch (e: ClientRequestException) {
            SerialResponse.Error(data = null, message = e.message)
        } catch (e: IOException) {
            SerialResponse.Error(data = null, message = e.message.orEmpty())
        }
}
