package com.example.baseapp.presentation

import com.example.baseapp.presentation.model.MovieDisplay

interface MovieStates {
    sealed class Event : UiEvent {
        data class OnMovieSelected(val id: Int) : Event()
    }

    sealed class State: UiState {
        data class ShowLatestMovies(val lastMovies: BaseState<List<MovieDisplay>>) : State()
    }

    sealed class Effect: UiEffect {
        data class GoToDetailMovie(val id: Int): Effect()
    }
}
