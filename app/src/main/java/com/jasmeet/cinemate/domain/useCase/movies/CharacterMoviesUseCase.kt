package com.jasmeet.cinemate.domain.useCase.movies

import com.jasmeet.cinemate.data.repository.movies.CharacterMoviesRepository
import com.jasmeet.cinemate.data.repository.movies.MoviesCastRepository


class CharacterMoviesUseCase(private val repository: CharacterMoviesRepository) {
    suspend operator fun invoke(id:String) = repository.getCharacterMovies(id)
}