package com.example.baseapp

import com.example.baseapp.db.MovieResponse

interface HttpRequest {
    fun get(url:String, params:String?, key:String?): MovieResponse?
}