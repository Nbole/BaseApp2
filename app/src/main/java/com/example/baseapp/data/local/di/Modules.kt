package com.example.baseapp.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.baseapp.data.MovieMapper
import com.example.baseapp.data.local.SwarmDb
import com.example.baseapp.data.local.model.dao.MovieDao
import com.example.baseapp.data.repository.MovieRepository
import com.example.baseapp.domain.MovieDataContract
import com.example.baseapp.domain.MovieRepositoryContract
import com.example.baseapp.domain.MoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocaleModules {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext appContext: Context): SwarmDb = Room
        .databaseBuilder(appContext, SwarmDb::class.java, SwarmDb.DATABASE_NAME)
        .build()

    @Provides
    @Singleton
    fun provideMovieDao(db: SwarmDb): MovieDao = db.movieDao()
}
