package com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.airingToday

data class AiringTodayResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)