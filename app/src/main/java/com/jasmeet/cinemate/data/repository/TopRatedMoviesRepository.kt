package com.jasmeet.cinemate.data.repository

import com.jasmeet.cinemate.data.apiResponse.remote.movies.topRated.TopRatedMoviesResponse

interface TopRatedMoviesRepository {
    suspend fun getTopRatedMovies(page:Int): TopRatedMoviesResponse
}