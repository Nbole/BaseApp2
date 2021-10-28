package com.example.baseapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import com.example.baseapp.db.PagedMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(movieRepository: MovieRepository) :
    ViewModel() {
    @ExperimentalPagingApi
    val latestMovies: LiveData<Resource<List<PagedMovies>>> =
        movieRepository.getLatestMovies().asLiveData()
}
