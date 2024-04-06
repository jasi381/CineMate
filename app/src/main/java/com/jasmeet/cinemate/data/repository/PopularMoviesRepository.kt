package com.jasmeet.cinemate.data.repository

import com.jasmeet.cinemate.data.data.apiResponse.remote.PopularMoviesResponse

interface PopularMoviesRepository {
    suspend fun getPopularMovies(page: Int): PopularMoviesResponse
}