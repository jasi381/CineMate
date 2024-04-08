package com.jasmeet.cinemate.data.repository

import com.jasmeet.cinemate.data.data.apiResponse.remote.movies.nowPlaying.NowPlayingResponse

interface NowPlayingMoviesRepository {
    suspend fun getNowPlayingMovies(page: Int): NowPlayingResponse
}