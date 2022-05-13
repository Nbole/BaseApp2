package com.example.baseapp.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.base.di.provideDispatchers
import com.example.baseapp.data.local.di.provideMovieDao
import com.example.baseapp.data.local.di.provideSqLDriver
import com.example.baseapp.data.remote.di.provideMovieDataContract
import com.example.baseapp.data.remote.di.provideSuggestedSearchEntityMapper
import com.example.baseapp.domain.di.provideMovieRepositoryContract
import com.example.baseapp.domain.di.provideMovieUseCase
import com.example.baseapp.domain.model.DResponse
import com.example.baseapp.domain.model.vo.MovieResponse
import com.example.baseapp.domain.usecase.MovieUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//@HiltViewModel
class MovieViewModel(moviesUseCase: MovieUseCase) : ViewModel() {
    //@Inject constructor(moviesUseCase: MoviesUseCase) : ViewModel() {
    val latestMovies: LiveData<DResponse<out List<MovieResponse>?>> =
        moviesUseCase.invoke().asLiveData()
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
