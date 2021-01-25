package com.example.baseproject.api

import com.example.baseproject.data.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {


    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") api_key: String?,
                          @Query("language") language: String?,
                          @Query("page") page: String?): Response<MovieResponse>
}