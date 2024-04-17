package com.jasmeet.cinemate.domain.useCase.movies

import com.jasmeet.cinemate.data.repository.movies.MoviesCastRepository

class MovieCastUseCase(private val repository: MoviesCastRepository) {
    suspend operator fun invoke(id:String) = repository.getMovieCast(id)
}