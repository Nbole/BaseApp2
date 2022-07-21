package com.example.baseapp.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.base.di.DispatchersProvider
import com.example.baseapp.MovieDataBase
import com.example.baseapp.data.local.model.NewsDb
import com.example.baseapp.data.local.model.dao.MovieDao
import com.example.baseapp.data.local.model.dao.MovieDaoImpl
import com.squareup.sqldelight.db.SqlDriver

fun provideNewsDb(app: Context): NewsDb = Room
    .databaseBuilder(app, NewsDb::class.java, NewsDb.DATABASE_NAME)
    .fallbackToDestructiveMigration()
    .build()

fun provideMovieDao(
    driver: SqlDriver,
    dispatchersProvider: DispatchersProvider
): MovieDao = MovieDaoImpl(
    db = MovieDataBase.invoke(driver),
    dispatchersProvider = dispatchersProvider
)
