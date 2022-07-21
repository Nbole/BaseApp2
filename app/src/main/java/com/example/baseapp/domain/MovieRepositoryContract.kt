package com.example.baseapp.domain

import androidx.paging.PagingData
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.data.local.model.db.Pepe
import com.example.baseapp.domain.model.DomainResponse
import com.example.baseapp.domain.model.vo.GenreResponse
import com.example.baseapp.domain.model.vo.MovieResponse
import com.example.baseapp.domain.model.vo.PreviewMovieResponse
import com.example.networkbound.NWResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryContract {
    fun getLatestMovies(): Flow<DomainResponse<List<PreviewMovieResponse>>>
    fun getMovie(id: Int): Flow<DomainResponse<MovieResponse?>>
    fun getGenres(): Flow<DomainResponse<List<GenreResponse>>>
}

interface NewsRepositoryContract {
    fun getHeaderNews(q: String): Flow<NWResponse<List<Pepe>>>
    fun getPagedHeaderNews(q: String): Flow<PagingData<HeaderField>>
}