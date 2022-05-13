package com.example.baseapp.data.remote.di

import android.util.Log
import com.example.base.mappers.BaseMapper
import com.example.baseapp.data.MovieMapper
import com.example.baseapp.data.remote.MovieDataApi
import com.example.baseapp.domain.MovieDataContract
import com.example.baseapp.domain.model.vo.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import movie.MovieEntity

fun provideMovieDataContract(): MovieDataContract = MovieDataApi()

fun provideSuggestedSearchEntityMapper(): BaseMapper<MovieEntity, MovieResponse> = MovieMapper()

object KtorClient {
    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }
    val httpClient = HttpClient {
        install(JsonFeature){
            serializer = KotlinxSerializer(json)
        }
        install(Logging){
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Network Message", "log: $message")
                }
            }
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 30_000
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}