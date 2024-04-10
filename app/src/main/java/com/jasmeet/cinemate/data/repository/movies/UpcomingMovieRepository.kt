package com.jasmeet.cinemate.data.repository.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.upComing.UpcomingMovieResponse

interface UpcomingMovieRepository {
    suspend fun getUpcomingMovies(page :Int):UpcomingMovieResponse
}