package com.example.baseapp

import com.example.baseapp.data.remote.model.MovieResponse

interface HttpRequest {
    fun get(url:String, params:String?, key:String?): MovieResponse?
}