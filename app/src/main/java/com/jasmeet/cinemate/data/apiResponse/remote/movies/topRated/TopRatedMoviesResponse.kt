package com.jasmeet.cinemate.data.apiResponse.remote.movies.topRated

data class TopRatedMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)