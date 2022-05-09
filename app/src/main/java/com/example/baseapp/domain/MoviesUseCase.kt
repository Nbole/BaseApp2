package com.example.baseapp.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val movieRepositoryContract: MovieRepositoryContract,
) {
    operator fun invoke(): Flow<DResponse<out List<MovieResponse>?>> {
      return movieRepositoryContract.getLatestMovies()
    }
}