package com.example.baseapp.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.paging.PagingData
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.domain.model.DomainResponse
import com.example.baseapp.domain.model.vo.HeaderFieldResponse
import com.example.baseapp.domain.usecase.NewsSearchUseCase
import org.koin.core.qualifier._q

class NewsViewModel(private val newsUseCase: NewsSearchUseCase) : ViewModel() {
    private val _query: MutableLiveData<String> = MutableLiveData()
    val pagedNews: LiveData<PagingData<HeaderField>> = _query.switchMap {
        newsUseCase.pagedHeaders(it)
    }
    fun setQuery(query: String) {
        _query.value = query
    }
}
