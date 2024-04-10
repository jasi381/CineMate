package com.jasmeet.cinemate.data.repositoryImpl.series

import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.topRated.TopRatedResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.series.TopRatedSeriesRepository

class TopRatedSeriesSeriesRepositoryImpl(private val apiService: ApiService): TopRatedSeriesRepository {
    override suspend fun getTopRatedSeries(page: Int): TopRatedResponse {
        return apiService.getTopRatedTvSeries(page)
    }
}