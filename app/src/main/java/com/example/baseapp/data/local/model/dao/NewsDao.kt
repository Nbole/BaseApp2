package com.example.baseapp.data.local.model.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baseapp.data.local.model.db.BodyField
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.data.local.model.db.HeaderTable
import com.example.baseapp.data.local.model.db.Pepe
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveHeaderFields(input:List<HeaderField>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBodyFields(input:List<BodyField>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRelatedTable(input:List<HeaderTable>)
    @Query("SELECT * FROM HeaderField INNER JOIN BodyField ON id = bodyId")
    fun loadAllHeaderFields(): Flow<List<Pepe>>
    @Query(
        """SELECT * FROM HeaderTable INNER JOIN HeaderField ON `query` ==:input
            WHERE `headerId` == id ORDER BY page ASC"""
    )
    fun load(input: String): PagingSource<Int, HeaderField>
    @Query("DELETE FROM HeaderField")
    fun deleteAllHeaderFields()
    @Query("DELETE FROM BodyField")
    fun deleteAllBodyFields()
    @Query("DELETE FROM HeaderTable")
    fun deleteRelatedTables()
    @Query(
        """SELECT page FROM HeaderTable WHERE `query` = :query AND
            headerId = :headerId"""
    )
    fun loadPage(query: String, headerId: String): Int
}
