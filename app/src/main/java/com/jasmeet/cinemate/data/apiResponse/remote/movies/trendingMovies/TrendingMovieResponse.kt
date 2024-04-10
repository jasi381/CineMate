package com.jasmeet.cinemate.data.apiResponse.remote.movies.trendingMovies

data class TrendingMovieResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)