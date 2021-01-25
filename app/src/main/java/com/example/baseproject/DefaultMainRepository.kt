package com.example.baseproject

import com.example.baseproject.api.MyApi
import com.example.baseproject.data.models.MovieResponse
import com.example.baseproject.util.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: MyApi
) : MainRepository {
    override suspend fun getMovies(
        apiKey: String,
        lang: String,
        page: String
    ): Resource<MovieResponse> {
        return try {
            val response = api.getTopRatedMovies(apiKey, lang, page)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured!")
        }
    }
}