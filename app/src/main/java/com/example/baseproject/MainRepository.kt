package com.example.baseproject

import com.example.baseproject.data.models.MovieResponse
import com.example.baseproject.util.Resource

interface MainRepository {
    suspend fun getMovies(apiKey:String, lang:String, page:String) : Resource<MovieResponse>
}