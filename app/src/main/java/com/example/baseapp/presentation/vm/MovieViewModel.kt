package com.example.baseapp.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.baseapp.domain.model.DResponse
import com.example.baseapp.domain.model.vo.MovieResponse
import com.example.baseapp.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(moviesUseCase: MoviesUseCase) : ViewModel() {
    val latestMovies: LiveData<DResponse<out List<MovieResponse>?>> =
        moviesUseCase.invoke().asLiveData()
}
