package com.example.baseapp.domain

import androidx.paging.PagingData
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.data.local.model.db.Pepe
import com.example.baseapp.data.local.model.db.TotalHeaders
import kotlinx.coroutines.flow.Flow

interface NewsRepositoryContract {
    fun getPagedHeaderNews(q: String): Flow<PagingData<HeaderField>>
    fun getResults(q: String): Flow<TotalHeaders>
    fun getPepe(q: String): Flow<Pepe>
}