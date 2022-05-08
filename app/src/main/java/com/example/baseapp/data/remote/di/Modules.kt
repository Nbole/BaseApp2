package com.example.baseapp.data.remote.di

import com.example.baseapp.MovieApi
import com.example.baseapp.MovieDataApi
import com.example.baseapp.MovieDataContract
import com.example.baseapp.data.local.model.dao.MovieDao
import com.example.baseapp.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

    @Singleton
    @Provides
    fun provideMovieRepository(
        db: MovieDao,
        movieDataContract: MovieDataContract,
    ): MovieRepository = MovieRepository(db, movieDataContract)

    @Provides
    fun provideMovieDataContract(
        movieApi: MovieApi
    ): MovieDataContract = MovieDataApi(movieApi)
}
