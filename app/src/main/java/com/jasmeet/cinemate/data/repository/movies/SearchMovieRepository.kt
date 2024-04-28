package com.jasmeet.cinemate.data.repository.movies

import com.jasmeet.cinemate.data.apiResponse.remote.movies.searchedMovies.SearchedMovieResponse

interface SearchMovieRepository {
    suspend fun getSearchMovie(query: String, page: Int): SearchedMovieResponse
}