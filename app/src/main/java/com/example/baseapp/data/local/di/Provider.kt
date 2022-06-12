package com.example.baseapp.data.local.di

import android.content.Context
import com.example.base.di.DispatchersProvider
import com.example.baseapp.MovieDataBase
import com.example.baseapp.data.local.model.dao.MovieDao
import com.example.baseapp.data.local.model.dao.MovieDaoImpl
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

fun provideSqLDriver(appContext: Context): SqlDriver {
    return AndroidSqliteDriver(
        schema = MovieDataBase.Schema,
        context = appContext,
        name = "cineView.db"
    )
}

fun provideMovieDao(
    driver: SqlDriver,
    dispatchersProvider: DispatchersProvider
): MovieDao = MovieDaoImpl(
    db = MovieDataBase.invoke(driver),
    dispatchersProvider = dispatchersProvider
)
