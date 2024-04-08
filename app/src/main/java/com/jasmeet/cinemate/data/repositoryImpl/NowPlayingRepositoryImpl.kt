package com.jasmeet.cinemate.data.repositoryImpl

import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.apiResponse.remote.movies.nowPlaying.NowPlayingResponse
import com.jasmeet.cinemate.data.repository.NowPlayingMoviesRepository

class NowPlayingRepositoryImpl(private val apiService: ApiService):NowPlayingMoviesRepository{
    override suspend fun getNowPlayingMovies(page: Int): NowPlayingResponse {
        return apiService.getNowPlayingMovies(page)
    }

}