package com.example.baseapp.data.remote.di

import com.example.baseapp.data.remote.MovieDataApi
import com.example.baseapp.data.remote.NewsDataApi
import com.example.baseapp.domain.MovieDataContract
import com.example.baseapp.domain.NewsDataContract

fun provideMovieDataContract(): MovieDataContract = MovieDataApi()

fun provideNewsDataContract(): NewsDataContract = NewsDataApi()
