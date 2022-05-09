package com.example.baseapp.domain.di

import com.example.baseapp.base.BaseMapper
import com.example.baseapp.base.Dispatchers
import com.example.baseapp.base.DispatchersProvider
import com.example.baseapp.data.MovieMapper
import com.example.baseapp.data.local.SwarmDb
import com.example.baseapp.data.local.model.db.Movie
import com.example.baseapp.data.repository.MovieRepository
import com.example.baseapp.domain.MovieDataContract
import com.example.baseapp.domain.MovieRepositoryContract
import com.example.baseapp.domain.model.vo.MovieResponse
import com.example.baseapp.domain.usecase.MoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocaleModules {
    @Provides
    fun provideMovieRepositoryContract(
        db: SwarmDb,
        movieDataContract: MovieDataContract,
        movieMapper: BaseMapper<Movie, MovieResponse>
    ): MovieRepositoryContract = MovieRepository(db.movieDao(), movieDataContract, movieMapper)

    @Provides
    @Singleton
    fun provideMovieUseCase(movieRepository: MovieRepositoryContract) =
        MoviesUseCase(movieRepository)
}