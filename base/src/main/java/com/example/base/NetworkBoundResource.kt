package com.example.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.Response

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDb: () -> Flow<ResultType>,
    crossinline netWorkRequest: suspend () -> Response<RequestType>,
    crossinline saveCall: suspend (Response<RequestType>) -> Unit
): Flow<WResponse<ResultType>> = flow {
    emit(WResponse.Loading(loadFromDb().firstOrNull()))
    val netWorkResponse: Response<RequestType> = netWorkRequest()
    emitAll(
        if (netWorkResponse.isSuccessful) {
            saveCall(netWorkResponse)
            loadFromDb().map { WResponse.Success(it) }
        } else {
            loadFromDb().map { WResponse.Error("Error", it) }
        }
    )
}.flowOn(Dispatchers.IO)
