package com.jasmeet.cinemate.domain.useCase

import com.jasmeet.cinemate.data.data.apiResponse.remote.movies.popular.PopularMoviesResponse
import com.jasmeet.cinemate.data.repository.PopularMoviesRepository

class PopularMoviesUseCase(private val repository: PopularMoviesRepository) {
    suspend operator fun invoke(): PopularMoviesResponse {
        return repository.getPopularMovies()
    }
}