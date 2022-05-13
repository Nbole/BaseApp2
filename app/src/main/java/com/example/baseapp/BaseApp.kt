package com.example.baseapp

import android.app.Application
import com.example.base.di.provideDispatchers
import com.example.baseapp.data.local.di.provideMovieDao
import com.example.baseapp.data.local.di.provideSqLDriver
import com.example.baseapp.data.remote.di.provideMovieDataContract
import com.example.baseapp.data.remote.di.provideSuggestedSearchEntityMapper
import com.example.baseapp.domain.di.provideMovieRepositoryContract
import com.example.baseapp.domain.di.provideMovieUseCase
import com.example.baseapp.presentation.vm.MovieViewModel
import org.koin.android.ext.koin.androidApplication
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
    single { provideSqLDriver(androidApplication()) }
    single { provideMovieDao(get(),get()) }
    single { provideMovieDataContract() }
    single { provideSuggestedSearchEntityMapper() }
    single { provideMovieRepositoryContract(get(), get(), get()) }
    single { provideDispatchers() }
    single { provideMovieUseCase(get(), get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
}
