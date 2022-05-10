package com.example.baseapp.data.remote.di

import android.util.Log
import com.example.base.mappers.BaseMapper
import com.example.baseapp.data.MovieMapper
import com.example.baseapp.data.local.model.db.Movie
import com.example.baseapp.data.remote.MovieApiContract
import com.example.baseapp.data.remote.MovieDataApi
import com.example.baseapp.domain.MovieDataContract
import com.example.baseapp.domain.model.vo.MovieResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteModules {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request()
            /* request =  request.newBuilder().header(
                 "Cache-Control",
                 "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
             ).build()*/
            chain.proceed(request)
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.themoviedb.org/3/movie/")
        .client(okHttpClient)
        .build()

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApiContract =
        retrofit.create(MovieApiContract::class.java)

    @Provides
    fun provideMovieDataContract(): MovieDataContract = MovieDataApi()

    @Provides
    @Singleton
    fun provideSuggestedSearchEntityMapper(): BaseMapper<Movie, MovieResponse> = MovieMapper()
}

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