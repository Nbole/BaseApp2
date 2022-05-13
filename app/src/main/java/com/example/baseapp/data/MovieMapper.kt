package com.example.baseapp.data

import com.example.base.mappers.BaseMapper
import com.example.baseapp.domain.model.vo.MovieResponse
import movie.MovieEntity

class MovieMapper: BaseMapper<MovieEntity, MovieResponse>() {
    override fun transform(inputModel: MovieEntity): MovieResponse {
       return MovieResponse(
            id = inputModel.id,
            posterPath = inputModel.posterPath,
            overview = inputModel.overview,
            title = inputModel.title
        )
    }
}
