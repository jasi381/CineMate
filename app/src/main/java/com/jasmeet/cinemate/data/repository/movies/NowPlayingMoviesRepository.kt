package com.jasmeet.cinemate.data.repository.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.nowPlaying.NowPlayingResponse

interface NowPlayingMoviesRepository {
    suspend fun getNowPlayingMovies(page: Int): NowPlayingResponse
}