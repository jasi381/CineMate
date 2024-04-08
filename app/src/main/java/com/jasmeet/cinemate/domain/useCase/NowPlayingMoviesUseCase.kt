package com.jasmeet.cinemate.domain.useCase

import com.jasmeet.cinemate.data.repository.NowPlayingMoviesRepository

class NowPlayingMoviesUseCase(private val repository: NowPlayingMoviesRepository){
    suspend operator fun invoke(page:Int) = repository.getNowPlayingMovies(page)
}