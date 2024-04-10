package com.jasmeet.cinemate.data.repositoryImpl.series

import com.jasmeet.cinemate.data.apiResponse.remote.tvSeries.trending.TrendingSeriesResponse
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.series.TrendingSeriesRepository

class TrendingSeriesRepositoryImpl(private val apiService: ApiService) : TrendingSeriesRepository {
    override suspend fun getTrendingSeries(): TrendingSeriesResponse {
        return apiService.getTrendingTvSeries()
    }
}