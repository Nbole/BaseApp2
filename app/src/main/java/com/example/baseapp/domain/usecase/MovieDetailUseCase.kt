package com.example.baseapp.domain.usecase

import com.example.base.di.DispatchersProvider
import com.example.baseapp.domain.MovieRepositoryContract
import com.example.baseapp.domain.model.DomainResponse
import com.example.baseapp.domain.model.vo.GenreResponse
import com.example.baseapp.domain.model.vo.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

interface MovieDetailUseCase {
    operator fun invoke(id: Int): Flow<GenreAndMovieDetail>
}

class MovieDetailUseCaseImpl constructor(
    private val movieRepositoryContract: MovieRepositoryContract,
    private val dispatcher: DispatchersProvider
) : MovieDetailUseCase {
    override operator fun invoke(id: Int): Flow<GenreAndMovieDetail> {
        val p: Flow<DomainResponse<List<GenreResponse>>> = movieRepositoryContract.getGenres()
        val o: Flow<DomainResponse<MovieResponse?>> =
            movieRepositoryContract.getMovie(id).flowOn(dispatcher.io)
        return p.combine(o) { first, second ->
            GenreAndMovieDetail(
                first,
                second
            )
        }
    }
}

data class GenreAndMovieDetail(
    val genres: DomainResponse<List<GenreResponse>>,
    val movie: DomainResponse<MovieResponse?>
)
