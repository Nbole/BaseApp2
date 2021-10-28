package com.example.baseapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.baseapp.db.PagedMovies

@Database(entities = [PagedMovies::class], version = 1)
abstract class SwarmDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME: String = "base_app"
    }
}
