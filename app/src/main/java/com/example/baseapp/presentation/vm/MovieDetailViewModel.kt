package com.example.baseapp.presentation.vm

import com.example.baseapp.domain.model.DomainResponse
import com.example.baseapp.domain.usecase.MovieDetailUseCase
import com.example.baseapp.presentation.BaseState
import com.example.baseapp.presentation.MovieDetailStates
import com.example.baseapp.presentation.model.GenreDisplay

class MovieDetailViewModel(
    private val movieDetailUseCase: MovieDetailUseCase,
) : BaseViewModel<MovieDetailStates.Event, MovieDetailStates.State, MovieDetailStates.Effect>() {

    suspend fun setMovieId(id: Int) {
        getMovie(id)
    }

    private suspend fun getMovie(id: Int) {
        movieDetailUseCase(id).collect { result ->
            val state: MovieDetailStates.State.ShowGenres = when (result.genres) {
                is DomainResponse.Success -> {
                    MovieDetailStates.State.ShowGenres(
                        BaseState.Success(
                            data = result.genres.data.map {
                                GenreDisplay(
                                    it.id,
                                    it.name
                                )
                            }
                        )
                    )
                }
                is DomainResponse.Loading -> {
                    MovieDetailStates.State.ShowGenres(
                        BaseState.Loading(
                            data = result.genres.data?.map {
                                GenreDisplay(
                                    it.id,
                                    it.name
                                )
                            }
                        )
                    )
                }
                is DomainResponse.Error -> {
                    MovieDetailStates.State.ShowGenres(
                        BaseState.Error
                    )
                }
            }
            setState {
                state
            }
        }
    }

    override fun createInitialState(): MovieDetailStates.State =
        MovieDetailStates.State.ShowDetails(BaseState.Idle)

    override fun handleEvent(event: MovieDetailStates.Event) {}
}