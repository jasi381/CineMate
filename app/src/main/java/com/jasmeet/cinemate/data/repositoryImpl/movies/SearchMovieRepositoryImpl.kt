package com.jasmeet.cinemate.data.repositoryImpl.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.searchedMovies.SearchedMovieResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.movies.SearchMovieRepository

class SearchMovieRepositoryImpl(private val apiService: ApiService) : SearchMovieRepository {
    override suspend fun getSearchMovie(query: String, page: Int): SearchedMovieResponse {
        return apiService.getSearchedMovies(query, page)
    }
}