package com.example.baseapp.domain.di

import com.example.base.DispatchersProvider
import com.example.base.mappers.BaseMapper
import com.example.baseapp.data.local.model.dao.MovieDao
import com.example.baseapp.data.repository.MovieRepository
import com.example.baseapp.domain.MovieDataContract
import com.example.baseapp.domain.MovieRepositoryContract
import com.example.baseapp.domain.model.vo.MovieResponse
import com.example.baseapp.domain.usecase.MovieUseCase
import com.example.baseapp.domain.usecase.MoviesUseCaseImpl
import movie.MovieEntity

fun provideMovieRepositoryContract(
    db: MovieDao,
    movieDataContract: MovieDataContract,
    movieMapper: BaseMapper<MovieEntity, MovieResponse>
): MovieRepositoryContract = MovieRepository(db, movieDataContract, movieMapper)

fun provideMovieUseCase(
    dispacher: DispatchersProvider,
    movieRepositoryContract: MovieRepositoryContract
): MovieUseCase = MoviesUseCaseImpl(movieRepositoryContract, dispacher)
