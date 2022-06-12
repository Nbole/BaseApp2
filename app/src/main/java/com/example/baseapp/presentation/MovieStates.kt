package com.example.baseapp.presentation

import com.example.baseapp.presentation.model.GenreDisplay
import com.example.baseapp.presentation.model.MovieDisplay
import com.example.baseapp.presentation.model.PreviewMovieDisplay

interface MovieStates {
    sealed class Event : UiEvent {
        data class OnMovieSelected(val id: Int) : Event()
    }

    sealed class State: UiState {
        data class ShowLatestMovies(val lastMovies: BaseState<List<PreviewMovieDisplay>>) : State()
    }

    sealed class Effect: UiEffect {
        data class GoToDetailMovie(val id: Int): Effect()
    }
}

interface MovieDetailStates {
    sealed class Event : UiEvent {
        data class OnMovieSelected(val id: Int) : Event()
    }

    sealed class State : UiState {
        data class ShowDetails(val movie: BaseState<MovieDisplay?>) : State()
        data class ShowGenres(val movie: BaseState<List<GenreDisplay>>) : State()
    }

    interface Effect : UiEffect
}

