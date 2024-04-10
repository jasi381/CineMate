package com.jasmeet.cinemate.data.repository.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.topRated.TopRatedMoviesResponse

interface TopRatedMoviesRepository {
    suspend fun getTopRatedMovies(page:Int): TopRatedMoviesResponse
}