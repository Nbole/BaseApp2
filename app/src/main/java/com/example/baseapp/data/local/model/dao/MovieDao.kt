package com.example.baseapp.data.local.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baseapp.data.local.model.db.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(pagedMovies: List<Movie>)

    @Query("SELECT * FROM Movie")
    fun loadMovies(): Flow<List<Movie>>
}
