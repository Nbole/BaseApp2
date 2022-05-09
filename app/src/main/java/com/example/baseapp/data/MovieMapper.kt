package com.example.baseapp.data

import com.example.baseapp.base.BaseMapper
import com.example.baseapp.data.local.model.db.Movie
import com.example.baseapp.domain.model.vo.MovieResponse

class MovieMapper: BaseMapper<Movie, MovieResponse>() {
    override fun transform(inputModel: Movie): MovieResponse {
       return MovieResponse(
            id = inputModel.id,
            posterPath = inputModel.posterPath,
            overview = inputModel.overview,
            title = inputModel.title
        )
    }
}