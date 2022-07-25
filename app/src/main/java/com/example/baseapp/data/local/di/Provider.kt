package com.example.baseapp.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.baseapp.data.local.model.NewsDb

fun provideNewsDb(app: Context): NewsDb = Room
    .databaseBuilder(app, NewsDb::class.java, NewsDb.DATABASE_NAME)
    .fallbackToDestructiveMigration()
    .build()
