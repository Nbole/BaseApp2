package com.example.baseapp.data

import com.example.baseapp.data.remote.SerialResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDb: () -> Flow<ResultType>,
    crossinline netWorkRequest: suspend () -> SerialResponse<RequestType>,
    crossinline saveCall: suspend (SerialResponse<RequestType>) -> Unit
): Flow<WResponse<ResultType>> = flow {
    emit(WResponse.Loading(loadFromDb().firstOrNull()))
    val netWorkSerialResponse: SerialResponse<RequestType> = netWorkRequest()
    emitAll(
        if (netWorkSerialResponse is SerialResponse.Success) {
            saveCall(netWorkSerialResponse)
            loadFromDb().map { WResponse.Success(it) }
        } else {
            val error = netWorkSerialResponse as SerialResponse.Error
            loadFromDb().map { WResponse.Error(error.message, it) }
        }
    )
}
