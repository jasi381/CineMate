package com.jasmeet.cinemate.domain.useCase.movies

import com.jasmeet.cinemate.data.repository.movies.MovieMediaRepository

class MovieMediaUseCase(private val repository : MovieMediaRepository) {
    suspend operator fun invoke(id:String) = repository.getMovieMedia(id)
}