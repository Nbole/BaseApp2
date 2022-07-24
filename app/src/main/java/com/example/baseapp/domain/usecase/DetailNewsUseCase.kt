package com.example.baseapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.baseapp.data.local.model.db.Pepe
import com.example.baseapp.domain.NewsRepositoryContract

interface DetailNewsUseCase {
    operator fun invoke(q: String): LiveData<Pepe>
}

class DetailNewsUseCaseImpl constructor(
    private val newsRepositoryContract: NewsRepositoryContract,
) : DetailNewsUseCase {
    override operator fun invoke(q: String): LiveData<Pepe> =
        newsRepositoryContract.getPepe(q).asLiveData()
}
