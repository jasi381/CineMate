package com.jasmeet.cinemate.data.repositoryImpl.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.media.MovieMediaResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.movies.MovieMediaRepository

class MovieMediaRepositoryImpl(private val apiService: ApiService): MovieMediaRepository {
    override suspend fun getMovieMedia(movieId: String): MovieMediaResponse {
        return apiService.getMovieImages(movieId)
    }
}