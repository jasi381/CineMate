package com.jasmeet.cinemate.data.repositoryImpl

import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.apiResponse.remote.movies.popular.PopularMoviesResponse
import com.jasmeet.cinemate.data.repository.PopularMoviesRepository

class PopularMoviesImpl(private val apiService: ApiService) : PopularMoviesRepository {
    override suspend fun getPopularMovies(): PopularMoviesResponse {
        return apiService.getPopularMovies()
    }
}
