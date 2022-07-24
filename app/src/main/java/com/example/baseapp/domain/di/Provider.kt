package com.example.baseapp.domain.di

import com.example.baseapp.data.local.model.NewsDb
import com.example.baseapp.data.remote.NewsApiApi
import com.example.baseapp.data.repository.NewsRepository
import com.example.baseapp.domain.NewsRepositoryContract
import com.example.baseapp.domain.usecase.DetailNewsUseCase
import com.example.baseapp.domain.usecase.DetailNewsUseCaseImpl
import com.example.baseapp.domain.usecase.HeaderAmountUseCase
import com.example.baseapp.domain.usecase.HeaderAmountUseCaseImpl
import com.example.baseapp.domain.usecase.NewsSearchUseCase
import com.example.baseapp.domain.usecase.NewsSearchUseCaseImpl

fun provideNewsRepositoryContract(
    db: NewsDb,
    api: NewsApiApi
): NewsRepositoryContract = NewsRepository(db, api)

fun provideNewsUseCase(
    newsRepositoryContract: NewsRepositoryContract
): NewsSearchUseCase = NewsSearchUseCaseImpl(newsRepositoryContract)

fun provideHeaderAmountUseCase(
    newsRepositoryContract: NewsRepositoryContract
): HeaderAmountUseCase = HeaderAmountUseCaseImpl(newsRepositoryContract)

fun provideDetailNewsUseCase(
    newsRepositoryContract: NewsRepositoryContract
): DetailNewsUseCase = DetailNewsUseCaseImpl(newsRepositoryContract)
