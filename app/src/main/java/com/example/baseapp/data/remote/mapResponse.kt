package com.example.baseapp.data.remote

import com.example.baseapp.data.WResponse
import com.example.baseapp.domain.model.DResponse

internal fun <I, O> WResponse<I>.mapResponse(transformAction: (I) -> O) =
    when (this) {
        is WResponse.Error -> {
            DResponse.Error(message.orEmpty())
        }
        is WResponse.Loading -> {
            DResponse.Loading(data?.let(transformAction::invoke))
        }
        is WResponse.Success -> {
            DResponse.Success(data?.let(transformAction::invoke))
        }
    }
