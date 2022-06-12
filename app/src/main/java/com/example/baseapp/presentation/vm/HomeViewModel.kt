package com.example.baseapp.presentation.vm

import androidx.lifecycle.viewModelScope
import com.example.baseapp.domain.model.DomainResponse
import com.example.baseapp.domain.usecase.PreviewMovieUseCase
import com.example.baseapp.presentation.BaseState
import com.example.baseapp.presentation.MovieStates
import com.example.baseapp.presentation.model.PreviewMovieDisplay
import kotlinx.coroutines.launch

class HomeViewModel(private val moviesUseCase: PreviewMovieUseCase) :
    BaseViewModel<MovieStates.Event, MovieStates.State, MovieStates.Effect>() {

    init {
        viewModelScope.launch {
            moviesUseCase().collect { result ->
                val state: MovieStates.State = when (result) {
                    is DomainResponse.Success -> {
                        MovieStates.State.ShowLatestMovies(
                            BaseState.Success(
                                data = result.data.map {
                                    PreviewMovieDisplay(
                                        it.id,
                                        it.title,
                                        it.posterPath
                                    )
                                }
                            )
                        )
                    }
                    is DomainResponse.Loading -> {
                        MovieStates.State.ShowLatestMovies(
                            BaseState.Loading(
                                data = result.data?.map {
                                    PreviewMovieDisplay(
                                        it.id,
                                        it.title,
                                        it.posterPath
                                    )
                                }
                            )
                        )
                    }
                    is DomainResponse.Error -> {
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
                setEffect {
                    MovieStates.Effect.GoToDetailMovie(event.id)
                }
            }
        }
    }
}