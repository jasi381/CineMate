package com.jasmeet.cinemate.domain.useCase.movies

import com.jasmeet.cinemate.data.repository.movies.UpcomingMovieRepository

class UpcomingMovieUseCase (private val repository: UpcomingMovieRepository){
    suspend operator fun invoke(page:Int) = repository.getUpcomingMovies(page)

}