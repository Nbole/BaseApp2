package com.example.baseapp.domain.usecase

import com.example.base.DispatchersProvider
import com.example.baseapp.domain.MovieRepositoryContract
import com.example.baseapp.domain.model.DResponse
import com.example.baseapp.domain.model.vo.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface MovieUseCase {
    operator fun invoke(): Flow<DResponse<out List<MovieResponse>?>>
}

class MoviesUseCaseImpl constructor(
//class MoviesUseCase @Inject constructor(
    private val movieRepositoryContract: MovieRepositoryContract,
    private val dispatcher: DispatchersProvider
): MovieUseCase {
    override operator fun invoke(): Flow<DResponse<out List<MovieResponse>?>> {
      return movieRepositoryContract.getLatestMovies().flowOn(dispatcher.io)
    }
}