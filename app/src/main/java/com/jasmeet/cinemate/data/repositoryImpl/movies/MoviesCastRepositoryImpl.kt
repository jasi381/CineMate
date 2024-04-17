package com.jasmeet.cinemate.data.repositoryImpl.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.castAndCrew.MovieCastResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.movies.MoviesCastRepository

class MoviesCastRepositoryImpl(private val apiService: ApiService):MoviesCastRepository {
    override suspend fun getMovieCast(movieId: String): MovieCastResponse {
        return apiService.getMovieCast(movieId)
    }
}