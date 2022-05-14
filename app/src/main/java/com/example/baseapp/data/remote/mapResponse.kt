package com.example.baseapp.data.remote

import com.example.baseapp.data.WResponse
import com.example.baseapp.domain.model.DomainResponse

internal fun <I, O> WResponse<I>.mapResponse(transformAction: (I) -> O) =
    when (this) {
        is WResponse.Error -> {
            DomainResponse.Error(message)
        }
        is WResponse.Loading -> {
            DomainResponse.Loading(data?.let(transformAction::invoke))
        }
        is WResponse.Success -> {
            DomainResponse.Success(transformAction.invoke(data))
        }
    }
