package com.jasmeet.cinemate.data.repository.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.trendingMovies.TrendingMovieResponse

interface TrendingMoviesRepository {
    suspend fun getTrendingMovies(): TrendingMovieResponse
}