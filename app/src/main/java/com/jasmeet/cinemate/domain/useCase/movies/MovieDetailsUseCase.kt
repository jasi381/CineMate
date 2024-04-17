package com.jasmeet.cinemate.domain.useCase.movies

import com.jasmeet.cinemate.data.repository.movies.MovieDetailsRepository


class MovieDetailsUseCase (private val repository: MovieDetailsRepository){
    suspend operator fun invoke(id:String) = repository.getMovieDetails(id)

}