package com.example.baseapp.presentation

import com.example.baseapp.domain.model.DResponse

internal fun <I, O> DResponse<I>.mapResponse(transformAction: (I) -> O) =
    when (this) {
        is DResponse.Error -> {
            BaseState.Error
        }
        is DResponse.Loading -> {
            BaseState.Loading(transformAction::invoke)
        }
        is DResponse.Success -> {
            BaseState.Success(transformAction::invoke)
        }
    }
