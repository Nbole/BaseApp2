package com.example.baseapp.presentation.vm

import androidx.lifecycle.viewModelScope
import com.example.baseapp.domain.model.DResponse
import com.example.baseapp.domain.usecase.MovieUseCase
import com.example.baseapp.presentation.BaseState
import com.example.baseapp.presentation.MovieStates
import com.example.baseapp.presentation.model.MovieDisplay
import kotlinx.coroutines.launch
import java.util.Collections.copy

class MovieViewModel(private val moviesUseCase: MovieUseCase) :
    BaseViewModel<MovieStates.Event, MovieStates.State, MovieStates.Effect>() {

    init {
        viewModelScope.launch {
            moviesUseCase().collect { result ->
                val state: MovieStates.State = when (result) {
                    is DResponse.Success -> {
                        MovieStates.State.ShowLatestMovies(
                            BaseState.Success(
                                data = result.data.map {
                                    MovieDisplay(
                                        it.id,
                                        it.title,
                                        it.overview,
                                        it.posterPath
                                    )
                                }
                            )
                        )
                    }
                    is DResponse.Loading -> {
                        MovieStates.State.ShowLatestMovies(
                            BaseState.Loading(
                                data = result.data?.map {
                                    MovieDisplay(
                                        it.id,
                                        it.title,
                                        it.overview,
                                        it.posterPath
                                    )
                                }
                            )
                        )
                    }
                    is DResponse.Error -> {
                        MovieStates.State.ShowLatestMovies(
                            BaseState.Error
                        )
                    }
                }
                setState {
                    state
                }
            }
        }
    }

    override fun createInitialState(): MovieStates.State = MovieStates.State.ShowLatestMovies(
        BaseState.Idle
    )

    override fun handleEvent(event: MovieStates.Event) {
        when (event) {
            is MovieStates.Event.OnMovieSelected -> {
            }
        }
    }
}