package com.jasmeet.cinemate.domain.useCase

import com.jasmeet.cinemate.data.data.apiResponse.remote.PopularMoviesResponse
import com.jasmeet.cinemate.data.repository.PopularMoviesRepository

class PopularMoviesUseCase(private val repository: PopularMoviesRepository) {
    suspend operator fun invoke(page: Int):PopularMoviesResponse{
        return repository.getPopularMovies(page)
    }
}