package com.example.baseapp.data

import com.example.baseapp.data.remote.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDb: () -> Flow<ResultType>,
    crossinline netWorkRequest: suspend () -> Response<RequestType>,
    crossinline saveCall: suspend (Response<RequestType>) -> Unit
): Flow<WResponse<ResultType>> = flow {
    emit(WResponse.Loading(loadFromDb().firstOrNull()))
    val netWorkResponse: Response<RequestType> = netWorkRequest()
    emitAll(
        if (netWorkResponse is Response.Success) {
            saveCall(netWorkResponse)
            loadFromDb().map { WResponse.Success(it) }
        } else {
            val error = netWorkResponse as Response.Error
            loadFromDb().map { WResponse.Error(error.message.orEmpty(), it) }
        }
    )
}
