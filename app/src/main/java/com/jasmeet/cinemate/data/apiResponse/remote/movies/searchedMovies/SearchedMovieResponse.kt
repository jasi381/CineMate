package com.jasmeet.cinemate.data.apiResponse.remote.movies.searchedMovies

data class SearchedMovieResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)