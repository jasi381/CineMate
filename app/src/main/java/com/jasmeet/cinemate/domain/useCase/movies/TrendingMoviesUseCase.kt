package com.jasmeet.cinemate.domain.useCase.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.trendingMovies.TrendingMovieResponse
import com.jasmeet.cinemate.data.repository.movies.TrendingMoviesRepository

class TrendingMoviesUseCase(private val repository: TrendingMoviesRepository) {
    suspend operator fun invoke(): TrendingMovieResponse {
        return repository.getTrendingMovies()
    }
}