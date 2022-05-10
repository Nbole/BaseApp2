package com.example.baseapp.data.remote

import android.util.Log
import com.example.baseapp.data.remote.di.KtorClient
import com.example.baseapp.data.remote.model.MovieResult
import com.example.baseapp.domain.MovieDataContract
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import java.io.IOException

class MovieDataApi: MovieDataContract {
    override suspend fun getLatestMovies(): Response<MovieResult> =
        try {
            val l = KtorClient.httpClient.get<MovieResult>{
                url("https://api.themoviedb.org/3/movie/now_playing")
                parameter("api_key","5e30e8afd06d2b8b9aae8eb164c85a29")
            }
            Response.Success(l)
        } catch (e: ClientRequestException) {
            Log.d("ktor", e.toString())
            Response.Error(data = null, message = e.message)
        } catch (e: IOException) {
            Log.d("ktor", e.toString())
            Response.Error(data = null, message = e.message.orEmpty())
        }
}
