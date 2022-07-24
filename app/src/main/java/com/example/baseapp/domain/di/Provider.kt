package com.example.baseapp.domain.di

import com.example.base.di.DispatchersProvider
import com.example.baseapp.data.local.model.NewsDb
import com.example.baseapp.data.local.model.dao.MovieDao
import com.example.baseapp.data.remote.NewsDataApi
import com.example.baseapp.data.repository.MovieRepository
import com.example.baseapp.data.repository.NewsRepository
import com.example.baseapp.domain.MovieDataContract
import com.example.baseapp.domain.MovieRepositoryContract
import com.example.baseapp.domain.NewsDataContract
import com.example.baseapp.domain.NewsRepositoryContract
import com.example.baseapp.domain.usecase.DetailNewsUseCase
import com.example.baseapp.domain.usecase.DetailNewsUseCaseImpl
import com.example.baseapp.domain.usecase.HeaderAmountUseCase
import com.example.baseapp.domain.usecase.HeaderAmountUseCaseImpl
import com.example.baseapp.domain.usecase.MovieDetailUseCase
import com.example.baseapp.domain.usecase.MovieDetailUseCaseImpl
import com.example.baseapp.domain.usecase.NewsSearchUseCase
import com.example.baseapp.domain.usecase.NewsSearchUseCaseImpl
import com.example.baseapp.domain.usecase.PreviewMovieUseCase
import com.example.baseapp.domain.usecase.PreviewMoviesUseCaseImpl

fun provideMovieRepositoryContract(
    db: MovieDao,
    movieDataContract: MovieDataContract,
): MovieRepositoryContract = MovieRepository(db, movieDataContract)

fun provideNewsRepositoryContract(
    db: NewsDb,
    api: NewsDataApi
): NewsRepositoryContract = NewsRepository(db, api)

fun providePreviewMovieUseCase(
    dispacher: DispatchersProvider,
    movieRepositoryContract: MovieRepositoryContract
): PreviewMovieUseCase = PreviewMoviesUseCaseImpl(movieRepositoryContract, dispacher)

fun provideMovieDetailUseCase(
    dispacher: DispatchersProvider,
    movieRepositoryContract: MovieRepositoryContract
): MovieDetailUseCase = MovieDetailUseCaseImpl(movieRepositoryContract, dispacher)

fun provideNewsUseCase(
    newsRepositoryContract: NewsRepositoryContract
): NewsSearchUseCase = NewsSearchUseCaseImpl(newsRepositoryContract)

fun provideHeaderAmountUseCase(
    newsRepositoryContract: NewsRepositoryContract
): HeaderAmountUseCase = HeaderAmountUseCaseImpl(newsRepositoryContract)

fun provideDetailNewsUseCase(
    newsRepositoryContract: NewsRepositoryContract
): DetailNewsUseCase = DetailNewsUseCaseImpl(newsRepositoryContract)
