package com.jasmeet.cinemate.data.repositoryImpl.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.upComing.UpcomingMovieResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.movies.UpcomingMovieRepository

class UpcomingMoviesRepositoryImpl(private val apiService: ApiService) : UpcomingMovieRepository {
    override suspend fun getUpcomingMovies(page: Int): UpcomingMovieResponse {
        return apiService.getUpcomingMovies(page)
    }
}