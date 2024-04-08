package com.jasmeet.cinemate.data.repository

import com.jasmeet.cinemate.data.data.apiResponse.remote.movies.popular.PopularMoviesResponse

interface PopularMoviesRepository {
    suspend fun getPopularMovies(): PopularMoviesResponse
}