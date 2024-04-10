package com.jasmeet.cinemate.data.repository.series

import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.topRated.TopRatedResponse

interface TopRatedSeriesRepository {
    suspend fun  getTopRatedSeries(page: Int): TopRatedResponse
}