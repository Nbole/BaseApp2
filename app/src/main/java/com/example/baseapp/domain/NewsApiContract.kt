package com.example.baseapp.domain
import com.example.baseapp.data.remote.model.NewResult
import com.example.networkbound.SerialResponse

interface NewsApiContract {
    suspend fun search(q: String, page: Int, size: Int): SerialResponse<NewResult>
}
