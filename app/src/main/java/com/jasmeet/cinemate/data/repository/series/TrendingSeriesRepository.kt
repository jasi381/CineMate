package com.jasmeet.cinemate.data.repository.series

import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.trending.TrendingSeriesResponse

interface TrendingSeriesRepository {
    suspend fun getTrendingSeries() : TrendingSeriesResponse
}