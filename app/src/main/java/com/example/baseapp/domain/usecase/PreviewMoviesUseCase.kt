package com.example.baseapp.domain.usecase

import com.example.base.di.DispatchersProvider
import com.example.baseapp.domain.MovieRepositoryContract
import com.example.baseapp.domain.model.DomainResponse
import com.example.baseapp.domain.model.vo.PreviewMovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface PreviewMovieUseCase {
    operator fun invoke(): Flow<DomainResponse<List<PreviewMovieResponse>>>
}

class PreviewMoviesUseCaseImpl constructor(
    private val movieRepositoryContract: MovieRepositoryContract,
    private val dispatcher: DispatchersProvider
): PreviewMovieUseCase {
    override operator fun invoke(): Flow<DomainResponse<List<PreviewMovieResponse>>> {
      return movieRepositoryContract.getLatestMovies().flowOn(dispatcher.io)
    }
}