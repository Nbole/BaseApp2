package com.example.baseapp

import android.app.Application
import com.example.base.di.provideDispatchers
import com.example.baseapp.data.local.di.provideMovieDao
import com.example.baseapp.data.local.di.provideNewsDb
import com.example.baseapp.data.remote.NewsApiApi
import com.example.baseapp.domain.di.provideDetailNewsUseCase
import com.example.baseapp.domain.di.provideHeaderAmountUseCase
import com.example.baseapp.domain.di.provideNewsRepositoryContract
import com.example.baseapp.domain.di.provideNewsUseCase
import com.example.baseapp.presentation.vm.DetailFragmentViewModel
import com.example.baseapp.presentation.vm.NewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val appModules = listOf(useCaseModule,viewModelModule)
        startKoin {
            androidContext(this@BaseApp)
            modules(appModules)
        }
    }
}

val useCaseModule = module {
    single { provideNewsDb(get()) }
    single { provideMovieDao(get(),get()) }
    single { NewsApiApi() }
    single { provideNewsRepositoryContract(get(), get()) }
    single { provideDispatchers() }
    single { provideNewsUseCase(get()) }
    single { provideDetailNewsUseCase(get()) }
    single { provideHeaderAmountUseCase(get()) }
}

val viewModelModule = module {
    viewModel { NewsViewModel(get(), get()) }
    viewModel { DetailFragmentViewModel(get()) }
}
