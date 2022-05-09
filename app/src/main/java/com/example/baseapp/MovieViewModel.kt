package com.example.baseapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import com.example.baseapp.data.local.model.db.Movie
import com.example.baseapp.data.repository.MovieRepository
import com.example.baseapp.domain.DResponse
import com.example.baseapp.domain.MovieResponse
import com.example.baseapp.domain.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(moviesUseCase: MoviesUseCase) : ViewModel() {
    val latestMovies: LiveData<DResponse<out List<MovieResponse>?>> =
        moviesUseCase.invoke().asLiveData()
}
