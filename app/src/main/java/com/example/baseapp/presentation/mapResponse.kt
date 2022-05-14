package com.example.baseapp.presentation

import com.example.baseapp.domain.model.DomainResponse

internal fun <I, O> DomainResponse<I>.mapResponse(transformAction: (I) -> O) =
    when (this) {
        is DomainResponse.Error -> {
            BaseState.Error
        }
        is DomainResponse.Loading -> {
            BaseState.Loading(transformAction::invoke)
        }
        is DomainResponse.Success -> {
            BaseState.Success(transformAction::invoke)
        }
    }
