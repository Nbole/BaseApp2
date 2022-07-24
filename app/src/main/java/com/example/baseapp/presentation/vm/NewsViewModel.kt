package com.example.baseapp.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.paging.PagingData
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.domain.usecase.HeaderAmountUseCase
import com.example.baseapp.domain.usecase.NewsSearchUseCase

class NewsViewModel(
    private val newsUseCase: NewsSearchUseCase,
    private val headerAmountUseCase: HeaderAmountUseCase
    ) : ViewModel() {
    private val _query: MutableLiveData<String> = MutableLiveData()
    val pagedNews: LiveData<PagingData<HeaderField>> = _query.switchMap {
        newsUseCase.pagedHeaders(it)
    }
    val resultAmount: LiveData<Int?> =
        _query.switchMap {
            headerAmountUseCase(it).map { result -> result?.total }
        }

    fun setQuery(query: String) {
        _query.value = query
    }
}
