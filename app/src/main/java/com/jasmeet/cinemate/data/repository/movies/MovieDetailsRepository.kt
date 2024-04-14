package com.jasmeet.cinemate.data.repository.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.details.MovieDetails

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId:String): MovieDetails
}