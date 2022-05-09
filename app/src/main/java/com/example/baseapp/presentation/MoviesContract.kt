package com.example.baseapp.presentation

import com.example.baseapp.Response
import com.example.baseapp.data.local.model.db.Movie

interface MoviesContract {
    fun getMovies(): Response<List<Movie>>
}