package com.jasmeet.cinemate.domain.useCase.movies

import com.jasmeet.cinemate.data.repository.UpcomingMovieRepository

class UpcomingMovieUseCase (private val repository: UpcomingMovieRepository){
    suspend fun getUpcomingMovies(page:Int) = repository.getUpcomingMovies(page)

}