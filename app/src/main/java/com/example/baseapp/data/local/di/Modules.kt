package com.example.baseapp.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.baseapp.data.local.SwarmDb
import com.example.baseapp.data.local.model.dao.MovieDao
import org.koin.dsl.module

/*@InstallIn(SingletonComponent::class)
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
}*/

fun provideDb(appContext: Context): SwarmDb = Room
    .databaseBuilder(appContext, SwarmDb::class.java, SwarmDb.DATABASE_NAME)
    .build()

fun provideMovieDao(db: SwarmDb): MovieDao = db.movieDao()
