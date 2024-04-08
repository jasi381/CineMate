package com.jasmeet.cinemate.data.apiResponse.remote.movies.nowPlaying

data class NowPlayingResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)