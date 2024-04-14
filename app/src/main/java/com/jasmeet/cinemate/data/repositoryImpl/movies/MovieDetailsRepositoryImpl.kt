package com.jasmeet.cinemate.data.repositoryImpl.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.details.MovieDetails
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.movies.MovieDetailsRepository


class MovieDetailsRepositoryImpl(private val apiService: ApiService) : MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: String): MovieDetails {
        return apiService.getMovieDetails(movieId)
    }
}
