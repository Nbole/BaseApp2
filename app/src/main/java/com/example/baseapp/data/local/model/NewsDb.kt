package com.example.baseapp.data.local.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.baseapp.data.local.model.dao.NewsDao
import com.example.baseapp.data.local.model.db.BodyField
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.data.local.model.db.HeaderTable

@Database(entities = [HeaderField::class, BodyField::class, HeaderTable::class], version = 1)
abstract class NewsDb : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    companion object { const val DATABASE_NAME: String = "news_db" }
}
