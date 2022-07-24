package com.example.baseapp.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.baseapp.data.local.model.db.Pepe
import com.example.baseapp.domain.usecase.DetailNewsUseCase

class DetailFragmentViewModel(
    private val detailNewsUseCase: DetailNewsUseCase,
) : ViewModel() {
    private val _id: MutableLiveData<String> = MutableLiveData()
    val body: LiveData<Pepe> = _id.switchMap { result ->
        detailNewsUseCase(result)?.let { it } ?: liveData {  }
    }
    fun setId(id:String) {
        _id.value = id
    }
}
