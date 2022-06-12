package com.example.baseapp.data.remote.di

import com.example.baseapp.data.remote.MovieDataApi
import com.example.baseapp.domain.MovieDataContract
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

fun provideMovieDataContract(): MovieDataContract = MovieDataApi()
