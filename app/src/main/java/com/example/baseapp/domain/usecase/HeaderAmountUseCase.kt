package com.example.baseapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.baseapp.data.local.model.db.TotalHeaders
import com.example.baseapp.domain.NewsRepositoryContract

interface HeaderAmountUseCase {
    operator fun invoke(q: String): LiveData<TotalHeaders>
}

class HeaderAmountUseCaseImpl constructor(
    private val newsRepositoryContract: NewsRepositoryContract,
) : HeaderAmountUseCase {
    override operator fun invoke(q: String): LiveData<TotalHeaders> =
        newsRepositoryContract.getResults(q).asLiveData()
}
