package com.example.baseapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.example.base.di.DispatchersProvider
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.data.local.model.db.Pepe
import com.example.baseapp.data.remote.mapResponse
import com.example.baseapp.domain.NewsRepositoryContract
import com.example.baseapp.domain.model.DomainResponse
import com.example.baseapp.domain.model.vo.HeaderFieldResponse
import com.example.networkbound.NWResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

interface NewsSearchUseCase {
    fun pagedHeaders(q: String): LiveData<PagingData<HeaderField>>
}

class NewsSearchUseCaseImpl constructor(
    private val newsRepositoryContract: NewsRepositoryContract,
    private val dispatcher: DispatchersProvider
) : NewsSearchUseCase {
    override fun pagedHeaders(q: String): LiveData<PagingData<HeaderField>> =
        newsRepositoryContract.getPagedHeaderNews(q).asLiveData()
}
