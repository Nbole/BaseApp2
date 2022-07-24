package com.example.baseapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.baseapp.data.local.model.NewsDb
import com.example.baseapp.data.local.model.dao.NewsDao
import com.example.baseapp.data.local.model.db.BodyField
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.data.local.model.db.HeaderTable
import com.example.baseapp.data.local.model.db.TotalHeaders
import com.example.baseapp.data.remote.NewsApiApi
import com.example.baseapp.data.remote.model.NewResult
import com.example.networkbound.SerialResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class PagedHeaderMediator(
    private val db: NewsDb,
    private val api: NewsApiApi,
    private val query: String,
) : RemoteMediator<Int, HeaderField>() {

    private val newsDao: NewsDao = db.newsDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeaderField>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> withContext(Dispatchers.IO) {
                state.lastItemOrNull()?.id?.let { newsDao.loadPage(query, it) + 1 } ?: 1
            }
        }

        return withContext(Dispatchers.IO) {
            val response: SerialResponse<NewResult> = api.search(query, page, 20)
            if (response is SerialResponse.Success) {
                val endOfPaginated = response.data.response.results?.isEmpty() ?: true

                val headerTables: List<HeaderTable> =
                    response.data.response.results.orEmpty().mapIndexed { index, newsDetail ->
                        HeaderTable(
                            headerId = newsDetail.id,
                            index = index,
                            page = page,
                            query = query
                        )
                    }

                if (loadType == LoadType.REFRESH) {
                    newsDao.deleteRelatedTables()
                    newsDao.deleteAllHeaderFields()
                    newsDao.deleteTotalHeaders()
                    newsDao.deleteAllBodyFields()
                }

                if (!endOfPaginated) {
                    db.runInTransaction {
                        response.data.response.total?.let {
                            db.newsDao().saveTotalHeader(
                                TotalHeaders(query,it)
                            )
                        } ?: newsDao.deleteTotalHeaders()
                        db.newsDao().saveRelatedTable(headerTables)
                        db.newsDao().saveBodyFields(
                            response.data.response.results.orEmpty().map {
                                BodyField(
                                    bodyId = it.id,
                                    headLine = it.fields?.headline.orEmpty(),
                                    body = it.fields?.body.orEmpty(),
                                )
                            }
                        )
                        db.newsDao().saveHeaderFields(
                            response.data.response.results.orEmpty().map {
                                HeaderField(
                                    id = it.id,
                                    webPublicationDate = it.webPublicationDate.orEmpty(),
                                    webTitle = it.webTitle.orEmpty(),
                                    thumbnail = it.fields?.thumbnail.orEmpty(),
                                )
                            }
                        )
                    }
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginated)
            } else {
                MediatorResult.Error(Exception("response null"))
            }
        }
    }
}