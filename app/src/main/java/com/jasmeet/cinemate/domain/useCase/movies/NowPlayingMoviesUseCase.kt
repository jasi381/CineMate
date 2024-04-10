package com.jasmeet.cinemate.domain.useCase.movies

import com.jasmeet.cinemate.data.repository.movies.NowPlayingMoviesRepository

class NowPlayingMoviesUseCase(private val repository: NowPlayingMoviesRepository){
    suspend operator fun invoke(page:Int) = repository.getNowPlayingMovies(page)
}