package com.jasmeet.cinemate.data.data.apiResponse.remote.movies.popular

data class PopularMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)