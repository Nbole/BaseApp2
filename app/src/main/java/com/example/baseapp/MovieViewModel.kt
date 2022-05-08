package com.example.baseapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import com.example.baseapp.data.local.model.db.Movie
import com.example.baseapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(movieRepository: MovieRepository) :
    ViewModel() {
    @ExperimentalPagingApi
    val latestPagedMovies: LiveData<Resource<List<Movie>>> =
        movieRepository.getLatestMovies().asLiveData()
}
