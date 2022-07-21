package com.example.baseapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.baseapp.data.local.model.NewsDb
import com.example.baseapp.data.local.model.db.BodyField
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.data.local.model.db.HeaderTable
import com.example.baseapp.data.local.model.db.Pepe
import com.example.baseapp.data.paging.PagedHeaderMediator
import com.example.baseapp.data.remote.NewsDataApi
import com.example.baseapp.data.remote.model.NewsDetail
import com.example.baseapp.domain.NewsRepositoryContract
import com.example.networkbound.NWResponse
import com.example.networkbound.SerialResponse
import com.example.networkbound.networkBoundResource
import kotlinx.coroutines.flow.Flow

class NewsRepository(
    private val db: NewsDb,
    private val api: NewsDataApi
) : NewsRepositoryContract {
    override fun getHeaderNews(q: String): Flow<NWResponse<List<Pepe>>> =
        networkBoundResource(
            { db.newsDao().loadAllHeaderFields() },
            { api.search(q, 1,20) },
            { response ->
                val results: List<NewsDetail>? =
                    (response as SerialResponse.Success).data.response.results
                if (!results.isNullOrEmpty()) {
                    db.newsDao().saveRelatedTable(
                        results.map { HeaderTable(
                            headerId = it.id,
                            query = "",
                            index = 2,
                            page = 2
                        ) }
                    )
                    db.newsDao().saveBodyFields(
                        results.map {
                            BodyField(
                                bodyId = it.id,
                                headLine = it.fields?.headline.orEmpty(),
                                body = it.fields?.body.orEmpty(),
                            )
                        }
                    )
                    db.newsDao().saveHeaderFields(
                        results.map {
                            HeaderField(
                                id = it.id,
                                webPublicationDate = it.webPublicationDate.orEmpty(),
                                webTitle = it.webTitle.orEmpty(),
                                thumbnail = it.fields?.thumbnail.orEmpty(),
                            )
                        }
                    )
                } else {
                    db.newsDao().deleteAllHeaderFields()
                    db.newsDao().deleteAllBodyFields()
                    db.newsDao().deleteRelatedTables()
                }
            }
        )

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedHeaderNews(q: String, ): Flow<PagingData<HeaderField>> {
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
}
