package com.jasmeet.cinemate.domain.useCase.movies

import com.jasmeet.cinemate.data.repository.movies.TopRatedMoviesRepository


class TopRatedMoviesUseCase(private val repository: TopRatedMoviesRepository){
    suspend operator fun invoke(page:Int) = repository.getTopRatedMovies(page)
}