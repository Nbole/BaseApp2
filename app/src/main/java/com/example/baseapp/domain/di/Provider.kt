package com.example.baseapp.domain.di

import com.example.base.di.DispatchersProvider
import com.example.baseapp.data.local.model.dao.MovieDao
import com.example.baseapp.data.repository.MovieRepository
import com.example.baseapp.domain.MovieDataContract
import com.example.baseapp.domain.MovieRepositoryContract
import com.example.baseapp.domain.usecase.MovieDetailUseCase
import com.example.baseapp.domain.usecase.MovieDetailUseCaseImpl
import com.example.baseapp.domain.usecase.PreviewMovieUseCase
import com.example.baseapp.domain.usecase.PreviewMoviesUseCaseImpl

fun provideMovieRepositoryContract(
    db: MovieDao,
    movieDataContract: MovieDataContract,
): MovieRepositoryContract = MovieRepository(db, movieDataContract)

fun providePreviewMovieUseCase(
    dispacher: DispatchersProvider,
    movieRepositoryContract: MovieRepositoryContract
): PreviewMovieUseCase = PreviewMoviesUseCaseImpl(movieRepositoryContract, dispacher)

fun provideMovieDetailUseCase(
    dispacher: DispatchersProvider,
    movieRepositoryContract: MovieRepositoryContract
): MovieDetailUseCase = MovieDetailUseCaseImpl(movieRepositoryContract, dispacher)
