package com.example.baseapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.domain.NewsRepositoryContract

interface NewsSearchUseCase {
    fun pagedHeaders(q: String): LiveData<PagingData<HeaderField>>
}

class NewsSearchUseCaseImpl constructor(
    private val newsRepositoryContract: NewsRepositoryContract,
) : NewsSearchUseCase {
    override fun pagedHeaders(q: String): LiveData<PagingData<HeaderField>> =
        newsRepositoryContract.getPagedHeaderNews(q).asLiveData()
}
