package com.jasmeet.cinemate.data.apiService

import com.jasmeet.cinemate.BuildConfig
import com.jasmeet.cinemate.data.data.apiResponse.remote.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        const val API_KEY = BuildConfig.api_key

    }

    @GET("top_rated?api_key=$API_KEY")
    suspend fun getPopularMovies(@Query("page") page: Int): PopularMoviesResponse
}
