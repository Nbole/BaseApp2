package com.example.baseapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.baseapp.data.local.model.NewsDb
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.data.local.model.db.TotalHeaders
import com.example.baseapp.data.paging.PagedHeaderMediator
import com.example.baseapp.data.remote.NewsDataApi
import com.example.baseapp.domain.NewsRepositoryContract
import kotlinx.coroutines.flow.Flow

class NewsRepository(
    private val db: NewsDb,
    private val api: NewsDataApi
) : NewsRepositoryContract {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedHeaderNews(q: String): Flow<PagingData<HeaderField>> {
        val mediator = PagedHeaderMediator(
            db = db,
            api = api,
            query = q
        )
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                initialLoadSize = 20
            ),
            remoteMediator = mediator,
            pagingSourceFactory = {
                db.newsDao().load(q)
            }
        ).flow
    }

    override fun getResults(q: String): Flow<TotalHeaders> = db.newsDao().loadTotalHeader(q)
}
