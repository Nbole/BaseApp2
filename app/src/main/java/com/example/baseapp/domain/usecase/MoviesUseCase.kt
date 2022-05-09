package com.example.baseapp.domain.usecase

import com.example.baseapp.domain.MovieRepositoryContract
import com.example.baseapp.domain.model.DResponse
import com.example.baseapp.domain.model.vo.MovieResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val movieRepositoryContract: MovieRepositoryContract,
) {
    operator fun invoke(): Flow<DResponse<out List<MovieResponse>?>> {
      return movieRepositoryContract.getLatestMovies()
    }
}