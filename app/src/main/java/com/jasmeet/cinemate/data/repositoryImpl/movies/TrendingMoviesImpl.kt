package com.jasmeet.cinemate.data.repositoryImpl.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.trendingMovies.TrendingMovieResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.movies.TrendingMoviesRepository

class TrendingMoviesImpl(private val apiService: ApiService) : TrendingMoviesRepository {
    override suspend fun getTrendingMovies(): TrendingMovieResponse {
        return apiService.getTrendingMovies()
    }
}
