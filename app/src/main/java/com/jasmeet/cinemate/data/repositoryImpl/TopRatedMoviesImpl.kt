package com.jasmeet.cinemate.data.repositoryImpl

import android.util.Log
import com.jasmeet.cinemate.data.apiResponse.remote.movies.topRated.TopRatedMoviesResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.TopRatedMoviesRepository

class TopRatedMoviesImpl(private val apiService: ApiService) : TopRatedMoviesRepository {
    override suspend fun getTopRatedMovies(page: Int): TopRatedMoviesResponse {
        return apiService.getTopRatedMovies(page)
    }
}